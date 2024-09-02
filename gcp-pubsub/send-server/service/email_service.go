package service

import (
	"context"
	"encoding/json"
	"math/rand"
	"pubsub/dto"
	"pubsub/global/common"
	"pubsub/global/config"
	"pubsub/model"
	"pubsub/repository"
	"time"
)

type EmailService struct {
	repository.EmailRepository
}

func NewEmailService(emailRepository repository.EmailRepository) *EmailService {
	return &EmailService{EmailRepository: emailRepository}
}

func (es *EmailService) SendEmail(ctx context.Context, pubsubMessage dto.PubSubMessage) error {
	// Transaction Start
	tx := es.EmailRepository.DB.Begin()
	defer func() {
		if err := recover(); err != nil {
			tx.Rollback()
		}
	}()

	// Validation - PubSub Topic Check
	if pubsubMessage.Subscription != config.GetEnvVar("GCP_PUBSUB_TOPIC") {
		tx.Rollback()
		return &common.InvalidSubscriptionError
	}

	// Validation - PubSub Authentication Key Check
	if pubsubMessage.Message.Attributes["authentication"] != config.GetEnvVar("AUTHENTICATION_KEY") {
		tx.Rollback()
		return &common.InvalidAuthenticationKeyError
	}

	// Validation - Cloud PubSub Message Decoding & Binding
	decodedData, decodeErr := pubsubMessage.DecodedData()
	if decodeErr != nil {
		tx.Rollback()
		return decodeErr
	}

	var emailRequest dto.EmailRequest
	if err := json.Unmarshal(decodedData, &emailRequest); err != nil {
		tx.Rollback()
		return err
	}
	email, findErr := es.EmailRepository.FindByID(ctx, tx, emailRequest.ID)
	if findErr != nil {
		tx.Rollback()
		return findErr
	}

	// Business Logic
	// ThirdParty Email Send - Random Response
	emailErr := randomResponse()

	// 발송 상태 업데이트
	if emailErr != nil {
		email.Status = model.FAILED
		es.EmailRepository.Update(ctx, tx, email)
	} else {
		email.Status = model.Sent
		es.EmailRepository.Update(ctx, tx, email)
	}

	// Transaction End
	if err := tx.Commit().Error; err != nil {
		tx.Rollback()
		return err
	}

	return nil
}

func randomResponse() error {
	// 시드 초기화
	rand.Seed(time.Now().UnixNano())

	// 0 또는 1 랜덤 생성
	randomBit := rand.Intn(2) // 0과 1 중 하나를 랜덤으로 반환

	// 0이면 에러 반환
	if randomBit == 0 {
		return &common.EmailError
	} else {
		return nil
	}
}

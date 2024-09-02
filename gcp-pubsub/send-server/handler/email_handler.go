package handler

import (
	"fmt"
	"net/http"
	"pubsub/dto"
	"pubsub/global/common"
	"pubsub/service"

	"github.com/labstack/echo/v4"
)

type EmailHandler struct {
	service.EmailService
}

func NewEmailHandler(emailService service.EmailService) *EmailHandler {
	return &EmailHandler{EmailService: emailService}
}

func (eh *EmailHandler) SendEmail(c echo.Context) error {
	// Request Data Binding
	var pubsubMessage dto.PubSubMessage

	// Request Body와 Request DTO 매핑, 오류 발생 시 401 반환
	err := c.Bind(&pubsubMessage)
	if err != nil {
		return err
	}

	fmt.Println(pubsubMessage)

	// Service Method
	ctx := c.Request().Context()
	serviceErr := eh.EmailService.SendEmail(ctx, pubsubMessage)
	if serviceErr != nil {
		return serviceErr
	}

	return c.JSON(http.StatusOK, common.NewSuccessResponseWithoutData())
}

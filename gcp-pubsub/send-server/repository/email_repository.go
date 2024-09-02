package repository

import (
	"context"
	"pubsub/model"

	"gorm.io/gorm"
)

type EmailRepository struct {
	DB *gorm.DB
}

func NewEmailRepository(db *gorm.DB) *EmailRepository {
	return &EmailRepository{DB: db}
}

func (er *EmailRepository) FindByID(ctx context.Context, tx *gorm.DB, emailId uint) (*model.Email, error) {
	var email model.Email
	if err := tx.WithContext(ctx).Where("id = ?", emailId).First(&email).Error; err != nil {
		return nil, err
	}
	return &email, nil
}

func (er *EmailRepository) Update(ctx context.Context, tx *gorm.DB, email *model.Email) error {
	if err := tx.WithContext(ctx).Save(email).Error; err != nil {
		return err
	}
	return nil
}

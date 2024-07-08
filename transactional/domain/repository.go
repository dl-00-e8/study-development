package domain

import (
	"context"

	"gorm.io/gorm"
)

type Repository struct {
	DB *gorm.DB
}

func NewRepository(db *gorm.DB) *Repository {
	return &Repository{db}
}

func (r *Repository) Create(ctx context.Context, tx *gorm.DB, test *Test) (*Test, error) {
	if err := tx.WithContext(ctx).Create(test).Error; err != nil {
		return nil, err
	}

	return test, nil
}

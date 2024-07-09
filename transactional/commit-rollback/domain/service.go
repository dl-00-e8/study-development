package domain

import "context"

type Service struct {
	Repository
}

func NewService(repository Repository) *Service {
	return &Service{repository}
}

func (s *Service) Register(ctx context.Context, data string) (*Test, error) {
	// Transaction Begin
	tx := s.Repository.DB.Begin()
	defer func() {
		if err := recover(); err != nil {
			tx.Rollback()
		}
	}()

	// Business Logic
	test := Test{
		Data: data,
	}
	saveTest, queryErr := s.Repository.Create(ctx, tx, &test)
	if queryErr != nil {
		return nil, queryErr
	}

	// Transaction Commit
	if err := tx.Commit().Error; err != nil {
		tx.Rollback()
		return nil, err
	}

	return saveTest, nil
}

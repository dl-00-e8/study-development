package model

import (
	"gorm.io/gorm"
)

type EmailStatus string

const (
	Pending EmailStatus = "PENDING"
	Sent    EmailStatus = "SENT"
	FAILED  EmailStatus = "FAILED"
)

type Email struct {
	// Base Column
	gorm.Model

	Title    string      `gorm:"column:title;not null"`
	Body     string      `gorm:"column:title;not null"`
	Receiver string      `gorm:"column:receiver;not null"`
	Status   EmailStatus `gorm:"column:status;not null"`
}

// 테이블명 매핑
func (Email) TableName() string {
	return "email"
}

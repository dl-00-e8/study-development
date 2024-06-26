package models

import (
	"time"

	"gorm.io/gorm"
)

type Email struct {
	// Base Column
	gorm.Model

	UUID         string `gorm:"column:uuid;not null"`
	Title        string `gorm:"column:title;not null"`
	Priority     int
	ProfitRate   string
	LTVRate      string
	Duration     int
	Constructor  string
	Body         string
	ThumbnailUrl string
	PageIMUrl    string
	TotalIMUrl   string
	Notice       string
	LoanDate     time.Time
	Spec         string
	Category     string
}

// 테이블명 매핑
func (Email) TableName() string {
	return "emails"
}

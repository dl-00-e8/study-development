package domain

import "gorm.io/gorm"

type Test struct {
	gorm.Model

	Data string `gorm:"column:data;not null"`
}

func (Test) TableName() string {
	return "test"
}

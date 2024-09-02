package common

import (
	"time"
)

type ErrorResponse struct {
	Timestamp time.Time `json:"timestamp"`
	Code      int       `json:"code"`
	Message   string    `json:"message"`
}

func NewErrorResponseWithErrorCode(e ErrorCode) *ErrorResponse {
	return &ErrorResponse{
		Timestamp: time.Now(),
		Code:      e.Code,
		Message:   e.Message,
	}
}

func NewErrorResponseWithErrorInterface(e error) *ErrorResponse {
	return &ErrorResponse{
		Timestamp: time.Now(),
		Code:      2000,
		Message:   e.Error(),
	}
}

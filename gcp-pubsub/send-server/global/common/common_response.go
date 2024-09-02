package common

import "time"

type ApplicationResponse struct {
	Timestamp time.Time `json:"timestamp"`
	Code      int       `json:"code"`
	Message   string    `json:"message"`
	Data      any       `json:"data,omitempty"`
}

func NewSuccessResponse(data any) *ApplicationResponse {
	return &ApplicationResponse{
		Timestamp: time.Now(),
		Code:      1000,
		Message:   "정상적인 요청입니다.",
		Data:      data,
	}
}

func NewSuccessResponseWithoutData() *ApplicationResponse {
	return &ApplicationResponse{
		Timestamp: time.Now(),
		Code:      1000,
		Message:   "정상적인 요청입니다.",
	}
}

package dto

import (
	"encoding/base64"
	"time"
)

type EmailRequest struct {
	ID       uint   `json:"id" validate:"required"`
	Title    string `json:"title" validate:"required"`
	Body     string `json:"body" validate:"required"`
	Receiver string `json:"receiver" validate:"required"`
}

type PubSubMessage struct {
	Message struct {
		Attributes  map[string]string `json:"attributes"`
		Data        string            `json:"data"`
		MessageID   string            `json:"messageId"`
		PublishTime time.Time         `json:"publishTime"`
	} `json:"message"`
	Subscription string `json:"subscription"`
}

func (m *PubSubMessage) DecodedData() ([]byte, error) {
	return base64.StdEncoding.DecodeString(m.Message.Data)
}

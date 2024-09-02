package router

import (
	"pubsub/handler"
	"pubsub/repository"
	"pubsub/service"

	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
)

func Init(e *echo.Echo, db *gorm.DB) {
	emailRepository := repository.NewEmailRepository(db)
	emailService := service.NewEmailService(*emailRepository)
	emailHandler := handler.NewEmailHandler(*emailService)

	e.POST("/api/v1/email/send", emailHandler.SendEmail)
}

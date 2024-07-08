package domain

import (
	"net/http"

	"github.com/labstack/echo/v4"
)

type Handler struct {
	Service
}

func NewHandler(service Service) *Handler {
	return &Handler{service}
}

func (h *Handler) Register(c echo.Context) error {
	dataStr := c.Param("data")

	ctx := c.Request().Context()
	test, serviceErr := h.Service.Register(ctx, dataStr)
	if serviceErr != nil {
		return serviceErr
	}

	return c.JSON(http.StatusOK,
		Response{
			ID:   test.ID,
			Data: test.Data,
		},
	)
}

type Response struct {
	ID   uint   `json:"id"`
	Data string `json:"data"`
}

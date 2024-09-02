package middleware

import (
	"errors"
	"net/http"
	"pubsub/global/common"

	"github.com/labstack/echo/v4"
)

func ErrorMiddleware(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		err := next(c)
		if err != nil {
			// 오류가 발생한 경우에는 바로 처리하고 종료합니다.
			handleError(c, err)
			return err
		}

		return nil // 오류가 없으면 nil을 반환합니다.
	}
}

func handleError(c echo.Context, err error) {
	var e *common.ErrorCode
	var statusCode int
	var response *common.ErrorResponse

	// 개발자 정의 에러인 경우와 아닌 경우를 분기하여 처리합니다.
	switch {
	case errors.As(err, &e):
		statusCode = e.HttpStatus
		response = common.NewErrorResponseWithErrorCode(*e)
	default:
		statusCode = http.StatusInternalServerError
		response = common.NewErrorResponseWithErrorInterface(err)
	}

	// Response 설정
	c.JSON(statusCode, response)
}

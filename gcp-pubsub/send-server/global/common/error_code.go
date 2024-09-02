package common

import (
	"net/http"
)

// ErrorCode 구조체 정의
type ErrorCode struct {
	HttpStatus int
	Code       int
	Message    string
}

// 에러 세부 정의
var (
	// 2000: Common Error
	InternalServerError           = ErrorCode{http.StatusInternalServerError, 2000, "서버 내부 오류입니다."}
	UnauthorizedError             = ErrorCode{http.StatusUnauthorized, 2001, "권한이 없는 요청입니다."}
	ForbiddenError                = ErrorCode{http.StatusForbidden, 2002, "인가되지 않은 요청입니다."}
	InvalidValueError             = ErrorCode{http.StatusBadRequest, 2003, "유효하지 않은 입력입니다."}
	NotFoundError                 = ErrorCode{http.StatusNotFound, 2004, "요청에 해당하는 자원을 찾을 수 없습니다."}
	EmailError                    = ErrorCode{http.StatusInternalServerError, 2005, "이메일 발송 중 오류가 발생했습니다."}
	InvalidSubscriptionError      = ErrorCode{http.StatusBadRequest, 2006, "유효하지 않은 구독 요청입니다."}
	InvalidAuthenticationKeyError = ErrorCode{http.StatusUnauthorized, 2007, "유효하지 않은 인증 키입니다."}
)

// error interface에 호환되도록 설정
func (e *ErrorCode) Error() string {
	return e.Message
}

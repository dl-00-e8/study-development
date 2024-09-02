package main

import (
	"log"
	"net/http"
	"pubsub/global/config"
	"pubsub/router"
	localMiddleware "pubsub/router/middleware"

	"github.com/labstack/echo/v4"
	echoMiddleware "github.com/labstack/echo/v4/middleware"
)

func main() {
	// Load Environment Variable
	config.LoadConfig()

	// DB Connection
	db := config.Connect()

	// Create Echo Instance
	e := echo.New()

	// Global Middleware Setting
	e.Use(echoMiddleware.Logger())
	e.Use(echoMiddleware.Recover())
	e.Use(echoMiddleware.CORSWithConfig(echoMiddleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowMethods: []string{http.MethodGet, http.MethodHead, http.MethodPut, http.MethodPatch, http.MethodPost, http.MethodDelete, http.MethodOptions},
		AllowHeaders: []string{"*"},
	}))
	e.Use(localMiddleware.ErrorMiddleware)

	// Router Setting
	router.Init(e, db)

	// HTTP Server Start
	if err := e.Start(":8081"); err != http.ErrServerClosed {
		log.Fatal(err)
	}
}

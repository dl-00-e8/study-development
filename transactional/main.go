package main

import (
	"log"
	"net/http"
	"os"
	"time"
	"transactional/domain"
	"transactional/global"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

// DB Connect Method
func Connect(dbHost string, dbPort string, dbUser string, dbPassword string, dbName string) *gorm.DB {
	var db *gorm.DB
	var err error

	// GORM Query Log 설정
	newLogger := logger.New(
		log.New(os.Stdout, "\r\n", log.LstdFlags), // io writer
		logger.Config{
			SlowThreshold:             time.Second, // Slow SQL threshold
			LogLevel:                  logger.Info, // Log level
			IgnoreRecordNotFoundError: true,        // Ignore ErrRecordNotFound error for logger
			ParameterizedQueries:      true,        // Don't include params in the SQL log
			Colorful:                  true,        // Disable color
		},
	)

	dsn := "host=" + dbHost + " user=" + dbUser + " password=" + dbPassword + " dbname=" + dbName + " port=" + dbPort + " sslmode=disable"

	db, err = gorm.Open(postgres.Open(dsn), &gorm.Config{
		Logger: newLogger,
	})

	if err != nil {
		log.Fatal("Could not connect to database!", err)
	}

	return db
}

func main() {
	// Load Environment Variable
	global.LoadConfig()

	// DB Connection
	dbHost := global.GetEnvVar("DB_HOST")
	dbPort := global.GetEnvVar("DB_PORT")
	dbUser := global.GetEnvVar("DB_USER")
	dbPassword := global.GetEnvVar("DB_PASSWORD")
	dbName := global.GetEnvVar("DB_NAME")
	db := Connect(dbHost, dbPort, dbUser, dbPassword, dbName)

	// Create Echo Instance
	e := echo.New()

	// Global Middleware Setting
	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowMethods: []string{http.MethodGet, http.MethodHead, http.MethodPut, http.MethodPatch, http.MethodPost, http.MethodDelete, http.MethodOptions},
		AllowHeaders: []string{"*"},
	}))

	repository := domain.NewRepository(db)
	service := domain.NewService(*repository)
	handler := domain.NewHandler(*service)

	e.POST("/v1/test/:data", handler.Register)

	// HTTP Server Start
	if err := e.Start(":8080"); err != http.ErrServerClosed {
		log.Fatal(err)
	}

}

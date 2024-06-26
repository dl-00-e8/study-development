package main

import (
	"log"
	"net/http"
	"os"
	"pubsub/config"
	"time"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
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
	config.LoadConfig()

	// DB Connection
	dbHost := config.GetEnvVar("DB_HOST")
	dbPort := config.GetEnvVar("DB_PORT")
	dbUser := config.GetEnvVar("DB_USER")
	dbPassword := config.GetEnvVar("DB_PASSWORD")
	dbName := config.GetEnvVar("DB_NAME")
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
	e.Use(middlewares.ErrorMiddleware)

	// Router Setting
	router.Init(e, db)

	// Create Cron Instance
	c := cron.New()

	// Cron Function
	// Every 9AM Send data to slack (UTC 기준으로 변동)
	c.AddFunc("0 0 1 * * *", func() {
		// c.AddFunc("@every 1m", func() {
		utils.DailyDataBatch(db)
	})

	// Cron start
	c.Start()

	// HTTP Server Start
	if err := e.Start(":80"); err != http.ErrServerClosed {
		log.Fatal(err)
	}

	// HTTPS Server Start
	// if err := e.StartTLS(":8443", "server.crt", "server.key"); err != http.ErrServerClosed {
	// 	log.Fatal(err)
	// }
}

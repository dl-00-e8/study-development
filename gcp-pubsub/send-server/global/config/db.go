package config

import (
	"fmt"
	"log"
	"os"
	"time"

	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

func Connect() *gorm.DB {
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

	dsn := GetEnvVar("DB_USER") + ":" + GetEnvVar("DB_PASSWORD") + "@tcp(" + GetEnvVar("DB_HOST") + ":" + GetEnvVar("DB_PORT") + ")/" + GetEnvVar("DB_NAME") + "?charset=utf8mb4&parseTime=True&loc=Local"
	db, err = gorm.Open(mysql.Open(dsn), &gorm.Config{
		Logger: newLogger,
	})

	if err != nil {
		log.Fatal("Could not connect to database!", err)
	}

	// 연결 풀 설정
	sqlDB, err := db.DB()
	if err != nil {
		log.Fatal(fmt.Errorf("failed to get database connection: %v", err))
	}

	// 유휴 커넥션 최대 수 설정
	sqlDB.SetMaxIdleConns(10)
	// 열ㄹ 커넥션 최대 수 설정
	sqlDB.SetMaxOpenConns(100)
	// 커넥션 최대 유지 시간 설정
	sqlDB.SetConnMaxLifetime(time.Hour)

	return db
}

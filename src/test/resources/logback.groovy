def LOG_PATH = "./logs"
def PATTERN = "%d %-5level %logger : %X %msg%n"

appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
    }
}

appender("File", RollingFileAppender) {
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_PATH}/%d{yyyy-MM-dd}.root.log"
    }
    encoder(PatternLayoutEncoder) {
        pattern = PATTERN
    }
}

logger("ru.tkachenko.app", DEBUG, ["Console-Appender", "File"], false)
root(INFO, ["Console-Appender", "File"])

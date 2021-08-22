package com.hyecheon.antteshop.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import org.springframework.context.annotation.Configuration
import java.text.MessageFormat
import java.util.*
import java.util.Arrays.stream
import java.util.function.Predicate
import javax.annotation.PostConstruct


/**
 * User: hyecheon lee
 * Email: rainbow880616@gmail.com
 * Date: 2021/08/22
 */
@Configuration
class P6spyConfig {
    @PostConstruct
    fun setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6spyPrettySqlFormatter::class.java.name
    }

    class P6spyPrettySqlFormatter : MessageFormattingStrategy {
        override fun formatMessage(
            connectionId: Int,
            now: String,
            elapsed: Long,
            category: String,
            prepared: String,
            sql: String,
            url: String,
        ): String {
            return sqlFormat(sql, category, getMessage(connectionId, elapsed, stackBuilder))
        }

        private fun sqlFormat(sql: String, category: String, message: String): String {
            if (Objects.isNull(sql.trim { it <= ' ' }) || sql.trim { it <= ' ' }.isEmpty()) {
                return ""
            }
            return StringBuilder()
                .append(NEW_LINE)
                .append(sqlFormat(sql, category))
                .append(message)
                .toString()
        }

        private fun sqlFormat(sql: String, category: String): String {
            if (isStatementDDL(sql, category)) {
                return FormatStyle.DDL
                    .formatter
                    .format(sql)
                    .replace("+0900", "")
            }
            return FormatStyle.BASIC
                .formatter
                .format(sql)
                .replace("+0900", "")
        }

        private fun isStatementDDL(sql: String, category: String): Boolean {
            return isStatement(category) && isDDL(sql.trim { it <= ' ' }.lowercase(Locale.ROOT))
        }

        private fun isStatement(category: String): Boolean {
            return Category.STATEMENT.name.equals(category)
        }

        private fun isDDL(lowerSql: String): Boolean {
            return lowerSql.startsWith(CREATE) || lowerSql.startsWith(ALTER) || lowerSql.startsWith(COMMENT)
        }

        private fun getMessage(connectionId: Int, elapsed: Long, callStackBuilder: StringBuilder): String {
            return StringBuilder()
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("\t").append(String.format("Connection ID: %s", connectionId))
                .append(NEW_LINE)
                .append("\t").append(String.format("Execution Time: %s ms", elapsed))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("\t").append(String.format("Call Stack (number 1 is entry point): %s", callStackBuilder))
                .append(NEW_LINE)
                .append(NEW_LINE)
                .append("----------------------------------------------------------------------------------------------------")
                .toString()
        }

        private val stackBuilder: StringBuilder
            get() {
                val callStack: Stack<String> = Stack()
                stream(Throwable().stackTrace)
                    .map(StackTraceElement::toString)
                    .filter(isExcludeWords)
                    .forEach(callStack::push)
                var order = 1
                val callStackBuilder: StringBuilder = StringBuilder()
                while (!callStack.empty()) {
                    callStackBuilder.append(MessageFormat.format("{0}\t\t{1}. {2}", NEW_LINE, order++, callStack.pop()))
                }
                return callStackBuilder
            }
        private val isExcludeWords: Predicate<String>
            get() = Predicate<String> { charSequence ->
                charSequence.startsWith(PACKAGE) && !charSequence.contains(
                    P6SPY_FORMATTER)
            }

        companion object {
            private val NEW_LINE: String = System.lineSeparator()
            private const val P6SPY_FORMATTER: String = "P6spyPrettySqlFormatter"
            private const val PACKAGE: String = "io.p6spy"
            private const val CREATE: String = "create"
            private const val ALTER: String = "alter"
            private const val COMMENT: String = "comment"
        }
    }
}
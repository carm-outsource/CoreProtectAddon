package cc.carm.outsource.plugin.coreprotectaddon.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeFormatUtils {

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // covert duration to 00:00:00 format (hh:mm:ss)
    public static String duration(long durationMillis) {
        long totalSeconds = durationMillis / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        // if hours is 0, return mm:ss format
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }

    }

    public static String duration(Duration duration) {
        return duration(duration.toMillis());
    }

    // covert localdatetime to yyyy-MM-dd HH:mm:ss format
    public static String datetime(java.time.LocalDateTime dateTime) {
        return FORMATTER.format(dateTime);
    }

    public static @NotNull String datetime(long seconds) {
        // 基于系统时区，将秒时间戳转换为日期时间字符串
        return FORMATTER.format(java.time.Instant.ofEpochSecond(seconds)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());
    }

    public static @Nullable Duration parse(@NotNull String input) {
        // 解析形如 "2h30m15s" 的字符串
        long totalMillis = 0;
        StringBuilder numberBuffer = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                numberBuffer.append(c);
            } else {
                if (numberBuffer.isEmpty()) {
                    return null; // 无效格式
                }
                long number = Long.parseLong(numberBuffer.toString());
                numberBuffer.setLength(0); // 清空缓冲区

                switch (c) {
                    case 'd' -> totalMillis += number * 24 * 60 * 60 * 1000L; // 天
                    case 'h' -> totalMillis += number * 60 * 60 * 1000L;      // 小时
                    case 'm' -> totalMillis += number * 60 * 1000L;           // 分钟
                    case 's' -> totalMillis += number * 1000L;                // 秒
                    default -> {
                        return null; // 无效格式
                    }
                }
            }
        }
        if (!numberBuffer.isEmpty()) {
            return null; // 无效格式
        }
        return Duration.ofMillis(totalMillis);
    }

    public static @Nullable Duration[] parseInterval(@NotNull String input) {
        String[] parts = input.split("-");
        if (parts.length > 2) {
            return null; // Invalid format, more than one '-'
        }

        Duration duration1;
        Duration duration2;

        if (parts.length == 1) {
            duration1 = parse(parts[0]);
            duration2 = Duration.ZERO; // Represents "now"
        } else { // parts.length == 2
            duration1 = parse(parts[0]);
            duration2 = parse(parts[1]);
        }

        if (duration1 == null || duration2 == null) {
            return null; // one of the parts is invalid
        }

        return new Duration[]{duration1, duration2};
    }

}

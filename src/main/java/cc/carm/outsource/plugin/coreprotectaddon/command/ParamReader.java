package cc.carm.outsource.plugin.coreprotectaddon.command;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface ParamReader {

    static Map<String, String> read(@NotNull String[] inputs) {
        Map<String, String> params = new HashMap<>();
        for (String input : inputs) {
            String[] parts = input.split(":", 2);
            if (parts.length == 2) {
                String key = parts[0].toLowerCase();
                String value = parts[1];
                params.put(key.toLowerCase(), value);
            }
        }
        return params;
    }

    static boolean validate(@NotNull String[] inputs, @NotNull String... keys) {
        for (String key : keys) {
            for (String input : inputs) {
                if (input.equalsIgnoreCase(key)) {
                    return true;
                }
            }
        }
        return false;
    }

}

package cc.carm.outsource.plugin.coreprotectaddon.data;

import java.util.Objects;
import java.util.UUID;

public record UserKey(long id, UUID uuid, String name) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserKey userKey)) return false;
        return id == userKey.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}

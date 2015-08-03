package io.sphere.sdk.channels.commands.updateactions;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * {@include.example io.sphere.sdk.channels.commands.ChannelUpdateCommandTest#changeKey()}
 */
public class ChangeKey extends UpdateActionImpl<Channel> {
    private final String key;

    private ChangeKey(final String key) {
        super("changeKey");
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static ChangeKey of(final String key) {
        return new ChangeKey(key);
    }
}

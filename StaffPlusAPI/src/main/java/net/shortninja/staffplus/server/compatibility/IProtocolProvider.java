package net.shortninja.staffplus.server.compatibility;

import net.shortninja.staffplus.IStaffPlus;

public interface IProtocolProvider {
    String getVersion();

    default boolean supports(String exactVersion) {
        return getVersion().equals(exactVersion);
    }

    IProtocol create(IStaffPlus staffPlus);
}

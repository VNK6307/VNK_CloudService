package wind.laguna.mycloud5.logout;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BlackList {
    private final Set<String> blackListTokenSet = new HashSet<>();

    public void blacklistToken(String token) {
        blackListTokenSet.add(token);
    }

    public boolean isBlackListed(String token) {
        return blackListTokenSet.contains(token);
    }
}

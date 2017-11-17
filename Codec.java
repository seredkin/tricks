import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
public class Codec {
    String prefix = "http://tinyurl.com/";
    AtomicLong seq = new AtomicLong();
    ConcurrentHashMap<Long, String> hashMap = new ConcurrentHashMap<>();
    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        Long l = seq.incrementAndGet();
        hashMap.put(l, longUrl);
        return prefix+l.toString();
    }
    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        Long id = Long.parseLong(shortUrl.replaceAll(prefix, ""));
        return hashMap.get(id);
    }
}

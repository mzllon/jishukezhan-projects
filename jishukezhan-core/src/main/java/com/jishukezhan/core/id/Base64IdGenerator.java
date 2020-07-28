package com.jishukezhan.core.id;

import com.jishukezhan.core.lang.Base64Util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 参考实现 <a href="https://github.com/elastic/elasticsearch/tree/master/server/src/main/java/org/elasticsearch/common">elasticsearch</a>
 *
 * @author miles.tang
 */
public class Base64IdGenerator implements IdGenerator<Object> {

    public static final SecureRandom INSTANCE = new SecureRandom();

    /**
     * 返回ID
     *
     * @param obj 参数
     * @return ID
     */
    @Override
    public String get(Object obj) {
        return getBase64UUID(INSTANCE);
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }


    /**
     * Returns a Base64 encoded version of a Version 4.0 compatible UUID
     * randomly initialized by the given {@link java.util.Random} instance
     * as defined here: http://www.ietf.org/rfc/rfc4122.txt
     */
    public String getBase64UUID(Random random) {
        return Base64Util.encode(getUUIDBytes(random), true);
    }

    private byte[] getUUIDBytes(Random random) {
        final byte[] randomBytes = new byte[16];
        random.nextBytes(randomBytes);
        /* Set the version to version 4 (see http://www.ietf.org/rfc/rfc4122.txt)
         * The randomly or pseudo-randomly generated version.
         * The version number is in the most significant 4 bits of the time
         * stamp (bits 4 through 7 of the time_hi_and_version field).*/
        randomBytes[6] &= 0x0f;  /* clear the 4 most significant bits for the version  */
        randomBytes[6] |= 0x40;  /* set the version to 0100 / 0x40 */

        /* Set the variant:
         * The high field of th clock sequence multiplexed with the variant.
         * We set only the MSB of the variant*/
        randomBytes[8] &= 0x3f;  /* clear the 2 most significant bits */
        randomBytes[8] |= 0x80;  /* set the variant (MSB is set)*/
        return randomBytes;
    }

}

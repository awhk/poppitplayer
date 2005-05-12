import java.util.zip.CRC32;
/**
 * 
 */

/**
 * @author Andrew
 *
 */
public class checksumtest {

    
    static final String test = "115129156148153";
    /**
     * @param args
     */
    public static void main(String[] args) {
        String test2 = "115129156148153";
        CRC32 checksum = new CRC32();
        checksum.update(test2.getBytes());
        System.out.print("String " + test2 + " has checksum ");
        System.out.println(Long.toString(checksum.getValue()));
    }

}

// Advay Koranne
// Andrew Merrill
// CS III

// Tifanys private key: 20756598093195912641

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.BitSet;
import java.util.jar.JarOutputStream;

public class KeyGenerator {
    private static SecureRandom rnd = new SecureRandom();
    private static BigInteger e;
    private static BigInteger n;
    private static BigInteger d;
    private static Object StringBuilder;

//  2255188081
//  4160961853
//  phi_n: 9383751569965124160
//  65;9383751576381274093;1970535885480805511
//  private key: 8670669086327647991

    public KeyGenerator() {
    }


    public static void main(String[] args) {
        dixons_algoritm("40657");
//        System.out.println("ok");
//        Key myKey = makeKey(" 72;3812181944669389192337;20457797492398430533");
//        myKey = fermatsAlgorithm(myKey);
//        System.out.println("done with key breaking");
//        String decryptedMessage = decrypt_message("289395698614544624043", myKey);
//        System.out.println("MESSAGE:" + decryptedMessage);
    }

    public static void ecnrypt_sentence(String sentence) {
        BigInteger[] encrypteMessage = encrypt_message(sentence);
        System.out.println((Arrays.toString(encrypteMessage)));
        StringBuilder M = new StringBuilder();
        for (int i = 0; i < encrypteMessage.length; i++) {
            M.append((encrypteMessage[i]));
            if (i < encrypteMessage.length - 1) {
                M.append(",");
            }
        }
        String message = M.toString();
        System.out.println(message);
    }

    public static Key makeKey(String key) {
        Key mykey = new Key();
        assert key != null;
        String[] arraystring = key.split(";");
        mykey.bits = arraystring[0];
        mykey.n = new BigInteger(arraystring[1]);
        mykey.e = new BigInteger(arraystring[2]);
        return mykey;
    }

    public static Key key_generator(int numBits) {
        BigInteger p = generate_large_primes(numBits);
        BigInteger q = generate_large_primes(numBits);
        System.out.println(p);
        System.out.println(q);
        n = p.multiply(q);
        BigInteger phi_n = n.subtract(p).subtract(q).add(BigInteger.ONE);
        boolean relatively_prime = false;
        do {
            e = new BigInteger(numBits, rnd);

            if (e.gcd(phi_n).equals(BigInteger.ONE) && ((e.compareTo(phi_n) < 0)) && ((e.compareTo(BigInteger.ONE) > 0))) {
                d = e.modInverse(phi_n);
                relatively_prime = true;
            } else {
                relatively_prime = false;
            }
        } while (relatively_prime == false);
        System.out.println("Public");
        System.out.println("phi_n" + phi_n); //phi_n: 9976196942516736

        System.out.println(numBits + ";" + n + ";" + e);
        System.out.println("private key: " + d);
        Key key = new Key();
        key.n = n;
        key.e = e;
        key.d = d;
        return key;
    }

    public static BigInteger generate_large_primes(int numBits) {
        BigInteger prime = new BigInteger((numBits / 2), 50, rnd);
        return prime;
    }

    public static BigInteger[] encrypt_message(String message) {
        byte[] bytearray = message.getBytes();
        BitSet bitset = BitSet.valueOf(bytearray);
        long[] longarray = bitset.toLongArray();
        BigInteger[] encryptedbigarray = new BigInteger[longarray.length];
        for (int i = 0; i < longarray.length; i++) {
            BigInteger bigInteger = BigInteger.valueOf(longarray[i]);
            encryptedbigarray[i] = encrypt(bigInteger);
        }
        return encryptedbigarray;
    }

    public static String decrypt_message(String m, Key key) {
        String[] arraystring = m.split(",");
        long[] decryptedLongArray = new long[arraystring.length];
        for (int i = 0; i < arraystring.length; i++) {
            BigInteger bigInteger = new BigInteger(arraystring[i]);
            BigInteger decyptedMessage = decryptMessage(bigInteger, key);
            decryptedLongArray[i] = (decyptedMessage.longValue());
        }
        BitSet bitset = BitSet.valueOf(decryptedLongArray);
        byte[] byteArray = bitset.toByteArray();
        String message = new String(byteArray);
        return message;
    }


    public static BigInteger encrypt(BigInteger S) {
        return S.modPow(d, n);
    }

    public static BigInteger decryptMessage(BigInteger M, Key key) {
        return M.modPow(key.d, key.n);
    }

    public static Key fermatsAlgorithm(Key key) {
        BigInteger n = new BigInteger(String.valueOf(key.n));
        BigInteger x = n.sqrt();
        BigInteger y = BigInteger.ZERO;
        int counter = 0;
        while (!((x.multiply(x).subtract(y.multiply(y)).equals(n)))) {
            if (((x.multiply(x).subtract(y.multiply(y)).compareTo(n) < 0))) {
                counter += 1;
                x = x.add(BigInteger.ONE);
            } else {
                y = y.add(BigInteger.ONE);
            }
        }
        BigInteger p = (x.add(y));
        BigInteger q = (x.subtract(y));
        n = p.multiply(q);
        BigInteger phi_n = n.subtract(p).subtract(q).add(BigInteger.ONE);
        System.out.println("phi_n" + phi_n);
        d = key.e.modInverse(phi_n);
        key.d = d;
        return key;
    }

    public static void dixons_algoritm(String n) {  // add 10 more rows than columns
        BigInteger number = new BigInteger(n);
        int counter = 0;
        int[][] matrix = new int[8][6];
        int[] prime_array = {2, 3, 5, 7};
        BigInteger r = (number.sqrt().add(BigInteger.ONE)); // Not true in perfect square
        for (int y = 0; y < matrix.length; y++) {
            System.out.println(y);
            matrix[y][0] = r.intValue();
            BigInteger fr = (r.pow(2).mod(number));
            matrix[y][1] = fr.intValue();
            counter += 1;
            for (int p = 0; p < prime_array.length; p++) {
                int num_i_factors = 0;
                while ((fr.mod(BigInteger.valueOf(prime_array[p])).equals(BigInteger.ZERO))) {
                    //   System.out.println((fr.mod(BigInteger.valueOf(prime_array[p]))));
                    num_i_factors += 1;
                    fr = (fr.divide(BigInteger.valueOf(prime_array[p])));
                }
                if (num_i_factors % 2 == 0) {
                    matrix[y][p + 2] = 0;
                } else {
                    matrix[y][p + 2] = 1;
                }
                //System.out.println(prime_array[p] + ":" + num_i_factors);
            }
            System.out.println(r);
            r = (r.add(BigInteger.ONE));
            fr = (r.pow(2).mod(number));
            System.out.println("-------------");
        }
        System.out.println(Arrays.deepToString(matrix));

    }





    public static void frfunction() {

    }

}
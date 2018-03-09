package util;

import edu.wpi.first.wpilibj.I2C;

public class PixyCamUtils {

	public static final int PIXY_START_WORD = 0xaa55;
	public static final int PIXY_START_WORDX = 0x55aa;
	
	public static PixyBlock getLargestBlock(I2C pixy) {
		
		PixyBlock block = new PixyBlock();
		byte[] frameBytes = new byte[14];
		for (int i=0; i<frameBytes.length; i++) {
			frameBytes[i]=0;
		}
		
		byte[] currentByte = new byte[2];
		pixy.readOnly(currentByte, 2);
		
		
	
		if (currentByte[1] == 0xaa && currentByte[0] == 0x55) {
			byte[] fudgeBuffer = new byte[1];
			pixy.readOnly(fudgeBuffer, 1);
		}
		
		System.out.println(((currentByte[1] & 0xff) << 8) | currentByte[0] & 0xff);
		
		if((((currentByte[1] & 0xff) << 8) | currentByte[0] & 0xff) != 43605) {
			return block;
		}
		pixy.readOnly(frameBytes, frameBytes.length);
		
		System.out.println(frameBytes[6] + 256);
		
		block.x = (int)(((frameBytes[7] & 0xff) << 8) | frameBytes[6] & 0xff);
		block.width = bytesToInt(frameBytes[11], frameBytes[10]);
		
		return block;
		
	}
	public static short bytesToInt(int msb,int lsb) {
		if (msb < 0) {
			msb+=256;
		}
		int value = msb * 256;
		if (lsb < 0) {
			lsb +=  256;
		}
		value += lsb;
		return (short) value;
	}
	
}

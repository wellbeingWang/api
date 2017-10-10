package com.fanmila.util.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fanmila.model.ModRange;

/**
 * 模区间工具类，根据不同需要提供模区间的生成和选择
 * @author caliph
 * 2014-03-17
 */
public class ModRangeUtils {
	/**
	 * 根据指定的模和区间数，生成均匀分配的模区间
	 * @param mod 模
	 * @param rangeNums 区间数
	 * @return
	 */
	public static List<ModRange> getModRangesAVG(int mod,int rangeNums)
	{
		int avg=mod/rangeNums;
		List<ModRange> modRanges=new ArrayList<ModRange>();
		for (int i = 1; i < rangeNums; i++) {
			ModRange modRange=new ModRange((i-1)*avg, i*avg);
			modRanges.add(modRange);
		}
		return modRanges;
	}
	/**
	 * 根据指定模区间数和当前时间获取按天变化的模区间索引
	 * @param rangeNums
	 * @return
	 */
	public static int getModRangeIndexByDayFromNowDate(int rangeNums)
	{
		long days=new Date().getTime()/(1000*60*60*24);
		return (int)days%rangeNums;
	}
	/**
	 * 根据指定的模和区间数，初始化一个模区间
	 * @param mod
	 * @param rangeNums
	 * @return
	 */
	public static ModRange getModRange(int mod,int rangeNums)
	{
		int avg=mod/rangeNums;
		ModRange modRange=new ModRange(mod, 0, avg);
		return modRange;
	}
	/**
	 * 根据一个模区间对象和重复率，生成一个新的模区间对象
	 * @param modRange 模区间对象
	 * @param rate 重复率
	 * @return
	 */
	public static ModRange getModRange(ModRange modRange,float rate)
	{
		int width=(modRange.getMod()+modRange.getMax()-modRange.getMin())%modRange.getMod();//区间宽度
		int repeat=Math.round(width*rate);
		modRange.setMax((modRange.getMax()+width-repeat)%modRange.getMod());
		modRange.setMin((modRange.getMin()+width-repeat)%modRange.getMod());
		return modRange;
	}
	/*public static void main(String[] args) {
		List<ModRange> modRanges=getModRangesAVG(100, 4);
		ModRange modRange=modRanges.get(getModRangeIndexByDayFromNowDate(4));
		System.out.println("["+modRange.getMin()+","+modRange.getMax()+"]");
		for (int i = 0; i < 50; i++) {
			int mod=UUIDUtils.moduuid("", "".length()-4, "".length(), 100);
			if (mod>=modRange.getMin()&&mod<modRange.getMax()) {
				System.out.println(mod);
			}
		}
	}*/
}

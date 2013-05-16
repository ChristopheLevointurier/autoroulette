package clv.sub;

import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.data.general.DefaultPieDataset;

public class DynamicPieDataSet extends DefaultPieDataset {
	private final int NBR_PARTS = 6;

	private ArrayList<Integer> rawdata = new ArrayList<Integer>();
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, String> labels = new HashMap<Integer, String>();

	private int delta = 1;

	public DynamicPieDataSet() {
		super();
		reset(0);
	}

	public void add(int i, boolean raw) {
		if (raw) {
			rawdata.add(i);
		}
		int index = (i / delta);
		// System.out.println("i=" + i + " index=" + index);
		if (index >= NBR_PARTS && raw) {
			reset(i);
		} else {
			addInData(index);
		}
		majModel();
	}

	private void majModel() {
		for (String ifinal : data.keySet())
			setValue(ifinal, data.get(ifinal));

	}

	private void addInData(int index) {// index=0,1,2,3...
		if (data.containsKey(labels.get(index))) {
			data.put(labels.get(index), data.remove(labels.get(index)) + 1);
		} else {
			// data.put("OMG:" + index, 1);
			System.out.println("bordel:" + index);
		}
	}

	private void reset(int valToPut) {
		delta = (valToPut / NBR_PARTS) + 1;
		data.clear();
		labels.clear();
		clear();
		System.out.print("\n--- Reset for " + valToPut + ":delta=" + delta + " labels:");
		for (Integer iip = 0; iip < NBR_PARTS; iip++) {
			String lab = (iip * delta) + "-" + (((iip + 1) * delta - 1));
			if (delta == 1) {
				lab = "" + iip;
			}
			System.out.print(lab + " ");
			labels.put(iip, lab);
			data.put(labels.get(iip), 0);
		}
		System.out.print(" --- \n");
		for (Integer ii : rawdata) {
			addInData(ii / delta);
		}
	}
}
package clv.sub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jfree.data.general.DefaultPieDataset;

public class DynamicPieDataSet extends DefaultPieDataset {

	private static final long serialVersionUID = -2664525063788462972L;

	private final int NBR_PARTS = 4;
	private ArrayList<Integer> rawdata = new ArrayList<Integer>();
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, String> labels = new HashMap<Integer, String>();
	private String title;
	private int delta = 1;
	//private Lock l = new ReentrantLock();

	public DynamicPieDataSet(String _title) {
		super();
		title = _title;
		reset(0);
	}

	public void add(int i) {
		// l.lock(); try {
		rawdata.add(i);
		int index = (i / delta);
		// System.out.println("i=" + i + " index=" + index);
		if (index >= NBR_PARTS) { // >= a cause du 0.
			reset(i);
		} else {
			addInData(index);
		}
		majModel();
		// } finally { l.unlock(); }
	}

	private void majModel() {
		for (String ifinal : data.keySet())
			setValue(ifinal, data.get(ifinal));

	}

	private void addInData(int index) {// index=0,1,2,3...
		String lab = labels.get(index);
		if (data.containsKey(lab)) {
			data.put(lab, data.remove(lab) + 1);
		} else {
			// data.put("OMG:" + index, 1);
			System.out.println("bordel:" + index);
		}
	}

	private void reset(int valToPut) {
		delta = (valToPut / NBR_PARTS) + 1;
		clear();
		labels.clear();
		data.clear();
		System.out.print("\n--- " + title + ": Reset for " + valToPut + ":delta=" + delta + " labels:");
		for (Integer iip = 0; iip < NBR_PARTS; iip++) {
			String lab = (iip * delta) + "-" + (((iip + 1) * delta - 1));
			if (delta == 1) {
				lab = "" + iip;
			}
			System.out.print(lab + " ");
			labels.put(iip, lab);
			data.put(labels.get(iip), 0);
		}
		System.out.print(" ---  refilling " + rawdata.size() + " values ");
		for (Integer ii : rawdata) {
			addInData(ii / delta);
		}
		System.out.println("done.");
	}

	public String getTitle() {
		return title;
	}
}
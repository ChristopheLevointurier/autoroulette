package clv.sub;

import java.util.ArrayList;
import java.util.HashMap;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.SortOrder;

public class DynamicPieDataSet extends DefaultPieDataset {

	private static final long serialVersionUID = -2664525063788462972L;

	private final int NBR_PARTS = 10;
	private ArrayList<Integer> rawdata = new ArrayList<Integer>();
	private HashMap<String, Integer> data = new HashMap<String, Integer>();
	private HashMap<Integer, String> labels = new HashMap<Integer, String>();
	private String title;
	private int delta = 1;

	public DynamicPieDataSet(String _title) {
		super();
		title = _title;
		reset(0);
	}

	public void add(int i) {
		rawdata.add(i);
		int index = (i / delta);
		// System.out.println("i=" + i + " index=" + index);
		if (index < NBR_PARTS) {
			addInData(index);
		} else // >= a cause du 0.
		{
			reset(i);
		}
		majModel();

	}

	private void majModel() {
		for (String ifinal : data.keySet())
			setValue(ifinal, data.get(ifinal));
	}

	private void addInData(int index) {// index=0,1,2,3...
		String lab = labels.get(index);
		data.put(lab, data.get(lab) + 1);
	}

	private void removeInData(String key) {
		setValue(key, -1);
	}

	public void removeEmptyParts() {
		for (String key : data.keySet()) {
			if (data.get(key) == 0)
				removeInData(key);
		}
	}

	private void reset(int valToPut) {
		delta = (valToPut / NBR_PARTS) + 1;
		for (String ifinal : data.keySet()) {
			removeInData(ifinal);
		}
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
		System.out.print(" ---  refilling " + rawdata.size() + " values... ");
		for (Integer ii : rawdata) {
			addInData(ii / delta);
		}
		for (String key : data.keySet())
			System.out.print(data.get(key) + "#");
	}

	public void sort(boolean key, SortOrder order) {
		if (key)
			sortByKeys(order);
		else
			sortByValues(order);
	}

	public String getTitle() {
		return title;
	}
}
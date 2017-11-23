package a.b.c.infra.tabledata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

@Data
public class TableData {

	private List<RowData> rows = new ArrayList<>();

	public void add(RowData rowData) {
		rows.add(rowData);
	}

	public boolean isEmpty() {
		return rows.isEmpty();
	}

	public List<String> getCaseNames() {
		if (rows.isEmpty()) {
			return Collections.emptyList();
		}

		return rows.get(0).getHeaderNames();
	}

}

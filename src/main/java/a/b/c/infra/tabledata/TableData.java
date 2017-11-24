package a.b.c.infra.tabledata;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TableData {

	private List<RowData> rows = new ArrayList<>();

	public void add(RowData rowData) {
		rows.add(rowData);
	}

	public boolean isEmpty() {
		return rows == null || rows.isEmpty();
	}

}

package a.b.c.infra.tabledata;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class TableDataCatalog {

	private Map<String, TableData> data = new HashMap<>();

	public TableData get(String name) {
		return data.get(name);
	}

	public void add(TableData table) {
		data.put(table.getName(), table);
	}

}

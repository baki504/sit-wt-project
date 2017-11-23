package a.b.c.infra.tabledata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RowData {

	private Map<String, String> data = new LinkedHashMap<>();

	public RowData(String columnName, String value) {
		setCellValue(columnName, value);
	}

	public void setCellValue(String columnName, String value) {
		data.put(columnName, value);
	}

	public List<String> getHeaderNames() {
		if (data.isEmpty()) {
			return Collections.emptyList();
		}

		List<String> headers = new ArrayList<>();

		for (Entry<String, String> string : data.entrySet()) {
			headers.add(string.getKey());
		}

		return headers;
	}

}

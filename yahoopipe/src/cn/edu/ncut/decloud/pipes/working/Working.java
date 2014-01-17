package cn.edu.ncut.decloud.pipes.working;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.ncut.decloud.pipes.modules.Module;
import cn.edu.ncut.decloud.pipes.modules.ModuleDao;
import cn.edu.ncut.decloud.pipes.pipe.PipeDao;
import cn.edu.ncut.decloud.pipes.wires.Wire;
import cn.edu.ncut.decloud.pipes.wires.WireDao;

public class Working {
	public Working(String pipeId, String jsonString) {
		PipeDao pipeDao = new PipeDao();
		// ֻ�����¼��������а���URL;
		ArrayList<String> urlModule = new ArrayList<String>();
		urlModule.add("fetchsitefeed");
		urlModule.add("xpathfetchpage");
		urlModule.add("fetch");
		urlModule.add("csv");
		urlModule.add("fetchdata");

		// ����module������Դ���ͻ��ǲ�������;
		ArrayList<String> sourceType = new ArrayList<String>();
		sourceType.add("fetchsitefeed");
		sourceType.add("ylocal");
		sourceType.add("xpathfetchpage");
		sourceType.add("fetch");
		sourceType.add("yql");
		sourceType.add("itembuilder");
		sourceType.add("flickr");
		sourceType.add("csv");
		sourceType.add("fetchdata");
		sourceType.add("rssitembuilder");
		sourceType.add("feedautodiscovery");

		// ��¼�ų����������;
		ArrayList<String> filterType = new ArrayList<String>();
		filterType.add("xpathfetchpage");
		filterType.add("yql");
		filterType.add("itembuilder");
		filterType.add("rssitembuilder");
		filterType.add("feedautodiscovery");
		filterType.add("privateinput");
		filterType.add("dateinput");
		filterType.add("locationinput");
		filterType.add("subelement");
		filterType.add("reverse");
		filterType.add("webservice");
		filterType.add("shortcuts");
		filterType.add("strreplace");
		filterType.add("stringtokenizer");
		filterType.add("substr");
		filterType.add("termextraction");
		filterType.add("strregex");
		filterType.add("privatestring");
		filterType.add("dateformat");
		filterType.add("locationbuilder");
		filterType.add("simplemath");

		if (!jsonString.contains("working")) {
			return;
		}
		JSONObject jsonPipe = JSONObject.fromObject(jsonString).getJSONObject("PIPE");
		JSONObject jsonWorking = jsonPipe.getJSONObject("working");
		JSONArray modulesArray = jsonWorking.getJSONArray("modules");
		// ��pipe�Ƿ���Ӧ���ų���module;
		ArrayList<String> moduleTypeList = new ArrayList<String>();
		for (int len = 0; len < modulesArray.size(); len++) {
			moduleTypeList.add(modulesArray.getJSONObject(len).getString("type"));
		}
		for (int len = 0; len < moduleTypeList.size(); len++) {
			if (filterType.contains(moduleTypeList.get(len))) {
				System.out.println("delete pipeId is:" + pipeId);
				pipeDao.addPipe(pipeId);
				return;
			}
		}

		setModulesList(pipeId, modulesArray, urlModule, sourceType, filterType);
		JSONArray wiresArray = jsonWorking.getJSONArray("wires");
		setWiresList(pipeId, wiresArray);
	}

	private void setModulesList(String pipeId, JSONArray modulesArray, ArrayList<String> urlModule, ArrayList<String> sourceType,
		ArrayList<String> filterType) {
		for (int i = 0; i < modulesArray.size(); i++) {
			// ��ȡmodule
			JSONObject jsonItem_module = modulesArray.getJSONObject(i);
			Module module = new Module();
			String id = jsonItem_module.getString("id");
			String type = jsonItem_module.getString("type");
			if (urlModule.contains(type)) {
				loadModuleUrl(module, jsonItem_module);
			} else {
				loadModule(module, jsonItem_module);
			}
			if (sourceType.contains(type)) {
				module.setSourceType("Source");
			} else {
				module.setSourceType("Operator");
			}
			module.setId(id);
			module.setType(type);
			module.setPipeId(pipeId);
			ModuleDao moduleDao = new ModuleDao();
			moduleDao.addModule(module);
		}
	}

	private void setWiresList(String pipeId, JSONArray wiresArray) {
		for (int i = 0; i < wiresArray.size(); i++) {
			// ��ȡÿһ��json����
			JSONObject jsonItem = wiresArray.getJSONObject(i);
			// ��ȡÿһ��json�����ֵ
			Wire wire = new Wire();
			wire.setPipeId(pipeId);
			wire.setId(jsonItem.getString("id"));
			wire.setSrcModuleId(jsonItem.getJSONObject("src").getString("moduleid"));
			wire.setTgtModuleId(jsonItem.getJSONObject("tgt").getString("moduleid"));
			ModuleDao moduleDao = new ModuleDao();
			Module srcModule = moduleDao.findModuleType(pipeId, jsonItem.getJSONObject("src").getString("moduleid"));
			Module tgtModule = moduleDao.findModuleType(pipeId, jsonItem.getJSONObject("tgt").getString("moduleid"));
			if (tgtModule.getType() != null) {
				wire.setSrcType(srcModule.getType());
				wire.setSrcUrl(srcModule.getUrl());
				wire.setTgtType(tgtModule.getType());
				wire.setTgtUrl(tgtModule.getUrl());
				WireDao wireDao = new WireDao();
				wireDao.addWire(wire);
			}
		}
	}

	private void loadModuleUrl(Module module, JSONObject jsonItem_module) {
		JSONObject json_feeds = jsonItem_module.getJSONObject("conf");
		/**
		 * 2013.7.19 URL��json���ж��ʱ�����飬ֻ��һ��ʱ�ǵ���Ԫ�أ������Ҫ�ֿ�����
		 * �����е��ַ�����Ӧ����Object���������getJSONObject������
		 * �����е��ַ�����Ӧ����Array����������getJSONArray������ ���򶼱��쳣��
		 * */
		if (json_feeds.toString().equals("{}")) {
			return;
		}
		Object json_URL = json_feeds.get("URL");
		String URLClassType = json_URL.getClass().getName();

		if ("net.sf.json.JSONArray".equals(URLClassType)) {// URL������
			JSONArray urlArray = json_feeds.getJSONArray("URL");
			StringBuilder url = new StringBuilder();
			for (int i = 0; i < urlArray.size() - 1; i++) {
				JSONObject jsonFeed = urlArray.getJSONObject(i);
				boolean terminalExist = jsonFeed.toString().contains("terminal");
				boolean valueExist = jsonFeed.toString().contains("value");
				String type = jsonFeed.getString("type");
				if (type.equals("url")) {
					if (terminalExist) {
						url.append(jsonFeed.getString("terminal"));
						url.append(",");
					} else if (valueExist) {
						url.append(jsonFeed.getString("value"));
						url.append(",");
					}
				}
			}
			JSONObject jsonFeed = urlArray.getJSONObject(urlArray.size() - 1);
			boolean terminalExist = jsonFeed.toString().contains("terminal");
			boolean valueExist = jsonFeed.toString().contains("value");
			String type = jsonFeed.getString("type");
			if (type.equals("url")) {
				if (terminalExist) {
					url.append(jsonFeed.getString("terminal"));
				} else if (valueExist) {
					url.append(jsonFeed.getString("value"));
				}
			}
			module.setUrl(url.toString());
		} else if ("net.sf.json.JSONObject".equals(URLClassType)) {// URL�ǵ���Ԫ��
			JSONObject singleURL = json_feeds.getJSONObject("URL");
			boolean terminalExist = singleURL.toString().contains("terminal");
			boolean valueExist = singleURL.toString().contains("value");
			String type = singleURL.getString("type");
			if (type.equals("url")) {
				if (valueExist) {
					String terminal = singleURL.getString("value");
					module.setUrl(terminal);
				} else if (terminalExist) {
					String terminal = singleURL.getString("terminal");
					module.setUrl(terminal);
				}
			}
		}
	}

	private void loadModule(Module module, JSONObject jsonItem_module) {
		module.setUrl("");
	}
}

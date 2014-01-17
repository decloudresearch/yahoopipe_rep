package cn.edu.ncut.decloud.pipes.main;

import java.net.MalformedURLException;
import java.util.ArrayList;

import org.dom4j.DocumentException;

import cn.edu.ncut.decloud.pipes.pipe.PipeDao;
import cn.edu.ncut.decloud.pipes.working.Working;

public class YahooPipesRun {
	// ��json�ļ��н�����module��wire,�����Ƽ���dataservice \operator��,�������Ƽ��ɹ�����;
	public static void main(String[] args) throws MalformedURLException, DocumentException {
		PipeDao pipeDao = new PipeDao();
		// ��json�ļ��н�����module��wire;
		int terminal = 0;
		// int totalPipeNum = pipeDao.getPipeCount();
		ArrayList<String> allPipeId = pipeDao.getAllPipeId();
		ArrayList<String> allPipeContent = pipeDao.getAllPipeContent();
		for (int i = 0; i < 4000; i++) {
			System.out.println("i ���� ��" + i + "id is :" + pipeDao.getPipeId().get(i));
			@SuppressWarnings("unused")
			Working working = new Working(allPipeId.get(i), allPipeContent.get(i));
			terminal++;
			if (terminal == 70) {
				terminal = 0;
				try {
					Thread.sleep(120000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
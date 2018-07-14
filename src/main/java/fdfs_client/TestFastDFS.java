package fdfs_client;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFastDFS {
	public String conf_filename = System.getProperty("user.dir")+"\\src\\main\\resources\\fdfs_client.conf";
	
	public String local_filename = "D:\\test.doc";//Ҫ�ϴ����ļ�

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpload() {

		try {
			ClientGlobal.init(conf_filename);

			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			// NameValuePair nvp = new NameValuePair("age", "18");
			NameValuePair nvp[] = new NameValuePair[] {
					new NameValuePair("name", "test_doc"),
					new NameValuePair("desc", "the test file") };
			String fileIds[] = storageClient.upload_file(local_filename, "doc",
					nvp);

			System.out.println(fileIds.length);
			System.out.println("������" + fileIds[0]);
			System.out.println("·��: " + fileIds[1]);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDownload() {
		try {

			ClientGlobal.init(conf_filename);

			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			byte[] b = storageClient.download_file("group1",
					"M00/00/00/wKjDgltBkACEPjBxAAAAAHfj3SA788.txt");
			System.out.println("byte's length:"+b.length);
			IOUtils.write(b, new FileOutputStream("D:/"
					+ UUID.randomUUID().toString() + ".txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFileInfo() {
		try {
			ClientGlobal.init(conf_filename);

			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			FileInfo fi = storageClient.get_file_info("group1",
					"M00/00/00/wKjDgltBkACEPjBxAAAAAHfj3SA788.txt");
			System.out.println(fi.getSourceIpAddr());
			System.out.println(fi.getFileSize());
			System.out.println(fi.getCreateTimestamp());
			System.out.println(fi.getCrc32());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFileMate() {
		try {
			ClientGlobal.init(conf_filename);

			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			NameValuePair nvps[] = storageClient.get_metadata("group1",
					"M00/00/00/wKjDgltBsCyATs9JAAAABzTycws375.doc");
			for (NameValuePair nvp : nvps) {
				System.out.println(nvp.getName() + ":" + nvp.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		try {
			ClientGlobal.init(conf_filename);

			TrackerClient tracker = new TrackerClient();
			TrackerServer trackerServer = tracker.getConnection();
			StorageServer storageServer = null;

			StorageClient storageClient = new StorageClient(trackerServer,
					storageServer);
			int i = storageClient.delete_file("group1",
					"M00/00/00/wKjDgltBsCyATs9JAAAABzTycws375.doc");
			System.out.println(i == 0 ? "ɾ���ɹ�" : "ɾ��ʧ��:" + i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

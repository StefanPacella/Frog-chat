package it.project.chat.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import it.project.chat.framework.data.BusinessException;

public class PictureUtility {

	private String path = "/home/dudu/eclipse/wks/progetto/src/main/webapp/picture_contact/";
	private String fileName;

	public PictureUtility() {
		// TODO Auto-generated constructor stub
	}

	public String saveTheImg(HttpServletRequest request, String emailUser) throws BusinessException {
		try {
			findPath();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new BusinessException("wrong path for the picture");
		}

		fileName = path + emailUser;

		deleteTheFile();

		try {
			save(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BusinessException("picture not valide");
		}

		if (!readTheFileImage()) {
			deleteTheFile();
			throw new BusinessException("picture not valide");
		}

		return "picture_contact/" + emailUser;
	}

	public boolean readTheFileImage() {
		try {
			if (ImageIO.read(new File(fileName)) == null)
				throw new IOException();
			else
				return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}

	private void save(HttpServletRequest request) throws Exception {
		DiskFileItemFactory fileFactory = new DiskFileItemFactory();
		fileFactory.setRepository(new File(path));

		ServletFileUpload upload = new ServletFileUpload(fileFactory);
		List<FileItem> fileItemsList = upload.parseRequest(request);
		Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();

		while (fileItemsIterator.hasNext()) {
			FileItem fileItem = fileItemsIterator.next();
			File file = new File(fileName);
			fileItem.write(file);
		}

	}

	private void deleteTheFile() {
		new File(fileName).delete();
	}

	private void findPath() throws FileNotFoundException {
		File catalinaBase = new File(System.getProperty("catalina.base")).getAbsoluteFile();
		File propertyFile = new File(catalinaBase, "webapps/");
		// path = propertyFile.getPath() + path;
	}

}

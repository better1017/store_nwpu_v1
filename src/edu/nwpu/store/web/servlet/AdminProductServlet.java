package edu.nwpu.store.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import edu.nwpu.store.domain.Category;
import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.CategoryService;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.service.serviceIml.CategoryServiceImp;
import edu.nwpu.store.service.serviceIml.ProductServiceImp;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.utils.UploadUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response)
			throws SQLException {
		// 获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		// 调用业务层查询全部商品信息返回PageModel
		ProductService ps = new ProductServiceImp();
		PageModel pm = ps.findAllProductsWithPage(curNum);
		// 将PageModel放入request
		// System.out.println(pm.getList());
		request.setAttribute("page", pm);
		// 转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";
	}

	// addProductUI
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ProductService ps = new ProductServiceImp();
		CategoryService cs = new CategoryServiceImp();
		// 获取全部分类
		List<Category> list = cs.getAllCategories();
		// 将全部分类放入request
		request.setAttribute("allCats", list);
		// 转发到/admin/product/add.jsp
		return "/admin/product/add.jsp";
	}

	// addProduct
	public String addProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// 存储表单中数据
		Map<String, String> map = new HashMap<String, String>();
		// 携带表单中的数据向service,dao传递
		Product product = new Product();
		try {
			// 以下三行代码功能: 通过request.getInputStream();获取到请求体的全部内容
			// 进行解析，将每对分割线的内容封装在了FileItem对象中
			DiskFileItemFactory fac = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fac);
			List<FileItem> list = upload.parseRequest(request);

			// 遍历集合
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 如果当前FileItem对象是普通项
					// 将普通项上name属性的值作为键，将获取到的内容作为值，放入Map中
					// {username:tom}{password:1234}
					map.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					// 如果当前的FileItem对象是上传项

					// 获取到原始的文件名称
					String oldFileName = item.getName();
					// 获取到要保存的文件的名称
					String newFileName = UploadUtils.getUUIDName(oldFileName);

					// 通过FileItem获取到输入流对象,通过输入流获取图片二进制数据
					InputStream is = item.getInputStream();
					// 获取文件在服务器项目下的真实路径
					String realPath = getServletContext().getRealPath("/products/3");
					String dir = UploadUtils.getDir(newFileName);
					String path = realPath + dir;
					// 内存中声明一个新的目录
					File newDir = new File(path);
					if (!newDir.exists()) {
						newDir.mkdirs();
					}
					// 在服务器端创建一个空文件（后缀必须和上传到服务端的文件名后缀一致）
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists()) {
						finalFile.createNewFile();
					}
					// 建立和空文件对应的输出流
					OutputStream os = new FileOutputStream(finalFile);
					// 将输入流中的数据刷到输出流中
					IOUtils.copy(is, os);
					// 释放资源
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					// 向map中存入一个键值对数据,{userhead:/image/11.bmp}
					map.put("pimage", "products/3" + dir + "/" + newFileName);
				}
			}

			// 利用BeanUtils将MAP中的数据填充到Product对象上
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPflag(0);
			// 利用service_dao将user上携带的数据存入数据仓库，重定向到查询全部商品信息
			ProductService ps =new ProductServiceImp();
			ps.saveProduct(product);
			//System.out.println("---------------------保存用户数据----------------------");
			response.sendRedirect("/store_nwpu_v1/AdminProductServlet?method=findAllProductsWithPage&num=1");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
}

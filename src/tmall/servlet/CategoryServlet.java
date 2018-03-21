package tmall.servlet;

import org.apache.commons.fileupload.FileItem;
import tmall.bean.Category;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package: tmall.servlet
 * @Author: WangXu
 * @CreateDate: 2018/3/19 15:11
 * @Version: 1.0
 */

@WebServlet("/categoryServlet")
public class CategoryServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        Map<String, String> params = new HashMap<>();
        InputStream inputStream = parseUpload(request, params);
        String name = params.get("name");
        Category category = new Category();
        category.setName(name);
        categoryDAO.add(category);

        File imageFolder = new File(request.getSession().getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, category.getId() + ".jpg");

        try {
            if (null != inputStream && 0 != inputStream.available()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte b[] = new byte[1024 * 1024];
                    int length = 0;
                    while (-1 != (length = inputStream.read(b))) {
                        fileOutputStream.write(b, 0, length);
                    }
                    fileOutputStream.flush();
                    //把文件保存成jpg格式

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categories = categoryDAO.list(page.getStart(), page.getCount());
        int total = categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs", categories);
        request.setAttribute("page", page);

        return "admin/listCategory.jsp";
    }
}
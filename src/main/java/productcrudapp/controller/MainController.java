package productcrudapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productcrudapp.dao.ProductDao;
import productcrudapp.model.Product;

@Controller
public class MainController {
	@Autowired
	private ProductDao productDao;

	@RequestMapping("/")
	public String Home(Model m) {
		List<Product> products = productDao.getProducts();
		m.addAttribute("products", products);
		return "index";
	}

	@RequestMapping("/add-product")
	public String AddProduct(Model m) {
		m.addAttribute("title", "Add product");
		return "add_product_form";
	}

	@RequestMapping(value = "/handle-product", method = RequestMethod.POST)
	public RedirectView HandleProduct(@ModelAttribute Product product, HttpServletRequest request) {
		System.out.println(product);

		try {
			productDao.createProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error creating product: " + e.getMessage());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");
		return redirectView;
	}

	@RequestMapping("/delete/{productId}")
	public RedirectView Deleteproduct(@PathVariable("productId") int productId, HttpServletRequest request) {

		try {
			productDao.deleteProduct(productId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error creating product: " + e.getMessage());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(request.getContextPath() + "/");
		return redirectView;
	}

	@RequestMapping("/update/{productId}")
	public String UpdateForm(@PathVariable("productId") int productId, Model model) {
		Product product = productDao.getProduct(productId);
		model.addAttribute("product", product);
		return "update_form";
	}
}

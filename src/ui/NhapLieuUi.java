package ui;

//Region Import
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterIOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.annotation.processing.Messager;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.synth.Region;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.ws.handler.MessageContext;



import connect.DoiXuLy;
import connect.DoiXuLyService;
import connect.GhiChu;
import connect.GhiChuService;
import connect.Huyen;
import connect.HuyenService;
import connect.KetNoi;
import connect.KhongThongKe;
import connect.KhongThongKeService;
import connect.LoaiViPham;
import connect.LoaiViPhamService;
import connect.TruongHopViPhamService2;
import connect.TuyenDuong;
import connect.TuyenDuongService;
import connect.Xa;
import connect.XaService;
import dulieu.DateRenderer;
import dulieu.KiemTraDuLieuDauVao;
import dulieu.TextAreaRenderer;
import dulieu.TruongHopViPham;
import dulieu.UpdateExcel;

import groupheader.ColumnGroup;
import groupheader.GroupableTableHeader;
import groupheader.GroupableTableHeaderUI;
import sorttherowheader.HeaderListener;
import sorttherowheader.SortButtonRenderer;

// EndRegion import

public class NhapLieuUi extends JFrame{
	
	private static final Object String = null;
	JMenuBar mnuBar;
	JMenu mnuFile;
	JMenu mnuHelp;
	JMenu mnuEdit;
	
	JMenuItem mnuFileNew;
	JMenuItem mnuFileOpen;
	JMenuItem mnuFileExit;
	JMenuItem mnuEditCopy;
	JMenuItem mnuEditPaste;
	//JMenuItem mnuHelpGui;
	//JMenuItem mnuHelpAbout;
	

	
	JButton btnThem,btnSua,btnXoa,btnCapNhap,btnLuu, btnNhapLai, btnTimKiem;
	
	JButton btnThongKe, btnBaoCao, btnTraCuu,btnMocThoiGian;
	
	JToolBar toolbar;
	
	DefaultMutableTreeNode root;
	JTree tree;
	
	
	DefaultTableModel dtm;
	JTable tblChiTietViPham;
	 JLabel lblThongTinTongQuat =new JLabel(); 
	
	JTextField txtSTT,txtNgayPhatSinh,txtThanhPhoHuyen,txtXa, txtNguoiXuLy,txtDoiXuLy,
	 txtTenDuong, txtLyTrinh, txtLoaiViPham;
	
	JTextField txtTimKiem;
	
	JTextArea txtaNguoiViPham,txtaNoiDungViPham,txtaKetQuaXuLy,txtaGhiChu,txtaGhiChuKhac;
	
	JTextField txtNgayLapBBKC,txtNgayCGBBKC,txtNgayLapBBVPHC, txtSoBBVPHC,txtNgayCGBBVPHC,txtSoBBCGVPHC;
	
	JTextField txtNgayQD, txtSoQuyetDinh,txtSoTien, txtNgayBBLV,txtSoBBLV,txtNgayBBCK;	
	JTextField txtSoBBCK, txtKhongThongKe,txtNgayThaoDo,txtNgayCuongChe; 
	JTextField txtNgayTuyenTruyen, txtSoLanTaiPham;
	
	JComboBox<LoaiViPham>cboLoaiViPham;
	LoaiViPhamService dsLoaiViPham;
	
	JComboBox<Huyen>cboHuyen;
	JComboBox<Xa>cboXa;
	JComboBox<TuyenDuong>cboTuyenDuong;
	JComboBox<DoiXuLy>cboDoiXuLy;
	
	JComboBox<GhiChu>cboGhiChu;
	JComboBox<KhongThongKe>cboKhongThongKe;
	
	TruongHopViPham dsTruongHopViPham =new TruongHopViPham();
	
	
	Connection conn=null;
	PreparedStatement preStatement=null;
	ResultSet result=null;
	
	SimpleDateFormat sdf_ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	Vector<TruongHopViPham> thvp=new Vector<TruongHopViPham>();
	
	public  NhapLieuUi (String title)
	{
		super(title);
		addControl();
		addEvent();
		conn = connect.KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA"); //tuong duong ham ket noi
		hienThiDSVPHLATDB();
		fillCboLoaiViPham();
		fillCboTuyenDuong();
		fillCboDoiXuLy();
		fillCboGhiChu();
		fillCboHuyen();
		fillCboXa();
		fillCboKhongThongKe();
		showWinDow2();
		xuLyNhap();
	}

public boolean kiemTraMaTonTai(String ma)
{
	try
	{
		String sql="select * from NHAPTHONGTIN where STT=?";
		preStatement=conn.prepareStatement(sql);
		preStatement.setString(1,ma);
		ResultSet rs=preStatement.executeQuery();
	return rs.next();
			
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
	
	return false;
}
	

int stt=0; // khai bao la bien toan cuc

public int laySTTCuoiCung()
{
	
	try
	{
		String sql="select MAX(STT) from NHAPTHONGTIN";
		preStatement=conn.prepareStatement(sql);		
		ResultSet rs=preStatement.executeQuery();

		while (rs.next())
		{
	return stt=rs.getInt(1);
	
		}
	
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}	
	return stt;
	
}



	
	private void hienThiDSVPHLATDB() {
		
		try
		{
			CallableStatement callStatement=conn.prepareCall("{call LayDSVPHLATDB}");
			result=callStatement.executeQuery();
			dtm.setRowCount(0);
			
			thvp=new Vector<TruongHopViPham>();		// phuc vu xuat ra ngoai
			
			while(result.next())
			{
				Vector<Object>vec =new Vector<Object>();
				vec.add(result.getInt("STT"));
				
				vec.add(result.getDate("NgayPhatSinh"));
				
				
				vec.add(result.getString("Huyen"));
				vec.add(result.getString("Xa"));
				vec.add(result.getString("NguoiXuLy"));
				vec.add(result.getString("DoiXuLy"));
				vec.add(result.getString("TenDuong"));
				vec.add(result.getString("LyTrinh"));
				vec.add(result.getString("NguoiViPham"));
				vec.add(result.getString("NoiDungViPham"));
				vec.add(result.getString("LoaiViPham"));
				
				vec.add(result.getDate("NgayLapBBKC"));
				vec.add(result.getDate("NgayChuyenGiaoBBKC"));
				vec.add(result.getDate("NgayLapBBVPHC"));
				vec.add(result.getString("SoBBVPHC"));
				
				vec.add(result.getDate("NgayChuyenGiaoBBVPHC"));
				vec.add(result.getString("SoBBChuyenGiao"));
				vec.add(result.getDate("NgayQuyetDinh"));
				vec.add(result.getString("SoQuyetDinh"));
				vec.add(result.getFloat("SoTien"));
				
				vec.add(result.getDate("NgayBBLV"));
				vec.add(result.getString("SoBBLV"));
				vec.add(result.getDate("NgayBBCamKet"));
				vec.add(result.getString("SoBBCamKet"));
				
				vec.add(result.getString("KetQuaXuLy"));
				vec.add(result.getString("GhiChu"));
				vec.add(result.getString("KhongThongKe"));
				
				vec.add(result.getDate("NgayThaoDo"));
				vec.add(result.getDate("NgayCuongChe"));
				vec.add(result.getDate("NgayTuyenTruyen"));
				vec.add(result.getInt("SoLanTaiPham"));
				vec.add(result.getString("GhiChuKhac"));
				
				dtm.addRow(vec);
				
				ThongTinTongQuat(dtm);
					
			// Phuc vu xuat ra ngoai				
			
			dsTruongHopViPham =new TruongHopViPham();
			
			
			dsTruongHopViPham.setsTT(result.getInt("STT"));
			dsTruongHopViPham.setNgayPhatSinh(result.getDate("NgayPhatSinh"));
			
			
			// cach chyen ult date sang sql date tuy nh
//			if(KiemTraDuLieuDauVao.KiemTraNgayThang2(result, "NgayPhatSinh")==null)
//			{
//				dsTruongHopViPham.setNgayPhatSinh(result.getDate("NgayPhatSinh"));
//				
//			}
//			else {
//				dsTruongHopViPham.setNgayPhatSinh(new java.sql.Date(sdf_ddMMyyyy.parse(KiemTraDuLieuDauVao.KiemTraNgayThang2(result, "NgayPhatSinh")).getTime()));
//			}
			
			dsTruongHopViPham.setThanhPhoHuyen(result.getString("Huyen"));
			dsTruongHopViPham.setXa(result.getString("Xa"));
			dsTruongHopViPham.setNguoiXuLy(result.getString("NguoiXuLy"));
			dsTruongHopViPham.setDoiXuLy(result.getString("DoiXuLy"));
			dsTruongHopViPham.setTuyenDuong(result.getString("TenDuong"));
			dsTruongHopViPham.setLyTrinh(result.getString("LyTrinh"));
			dsTruongHopViPham.setNguoiViPham(result.getString("NguoiViPham"));
			dsTruongHopViPham.setNoiDungViPham(result.getString("NoiDungViPham"));
			dsTruongHopViPham.setLoaiViPham(result.getString("LoaiViPham"));
			
			dsTruongHopViPham.setNgayLapBBKC(result.getDate("NgayLapBBKC"));
			dsTruongHopViPham.setNgayChuyenGiaoBBKC(result.getDate("NgayChuyenGiaoBBKC"));
			dsTruongHopViPham.setNgayLapBBVPHC(result.getDate("NgayLapBBVPHC"));
			dsTruongHopViPham.setSoBBVPHC(result.getString("SoBBVPHC"));
			
			dsTruongHopViPham.setNgayChuyenGiaoBBVPHC(result.getDate("NgayChuyenGiaoBBVPHC"));
			dsTruongHopViPham.setSoBBChuyenGiaoBBVPHC(result.getString("SoBBChuyenGiao"));
			dsTruongHopViPham.setNgayQuyetDinh(result.getDate("NgayQuyetDinh"));
			dsTruongHopViPham.setSoQuyetDinh(result.getString("SoQuyetDinh"));
			dsTruongHopViPham.setSoTien(result.getFloat("SoTien"));
			
			dsTruongHopViPham.setNgayLapBBLV(result.getDate("NgayBBLV"));
			dsTruongHopViPham.setSoBBLV(result.getString("SoBBLV"));
			dsTruongHopViPham.setNgayLapBBCK(result.getDate("NgayBBCamKet"));
			dsTruongHopViPham.setSoBBCK(result.getString("SoBBCamKet"));
			
			dsTruongHopViPham.setKetQuaXuLy(result.getString("KetQuaXuLy"));
			dsTruongHopViPham.setGhiChu(result.getString("GhiChu"));
			dsTruongHopViPham.setKhongThongKe(result.getString("KhongThongKe"));
			
			dsTruongHopViPham.setNgayThaoDo(result.getDate("NgayThaoDo"));
			dsTruongHopViPham.setNgayCuongChe(result.getDate("NgayCuongChe"));
			dsTruongHopViPham.setNgayTuyenTruyen(result.getDate("NgayTuyenTruyen"));
			dsTruongHopViPham.setSoLanTaiPham(result.getInt("SoLanTaiPham"));
			dsTruongHopViPham.setGhiChuKhac(result.getString("GhiChuKhac"));
			
			
			thvp.add(dsTruongHopViPham);
			
			
			
			}
			
			
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
	}

	
	public void addControl()
	{
		
		mnuBar=new JMenuBar();
		setJMenuBar(mnuBar);
		mnuFile=new JMenu("File");
		mnuEdit=new JMenu("Edit");
		mnuHelp=new JMenu("Help");
		
		mnuFileNew=new JMenuItem("New");
		
		// TAO PHIM TAT CHO MANG
		mnuFileNew.setAccelerator(KeyStroke.getKeyStroke('N',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));;
		
		
		mnuFileOpen=new JMenuItem("Open");
		mnuFileExit=new JMenuItem("Exit");
		mnuFileExit.setIcon(new ImageIcon("hinh/exit.png"));// thiet lap hinh anh cho menu
		
		
		mnuEditCopy=new JMenuItem("Copy");
		mnuEditPaste=new JMenuItem("Paste");		
		
		
		mnuFile.add(mnuFileNew);
		mnuFile.add(mnuFileOpen);
		mnuFile.addSeparator();		// tao dunog gach
		mnuFile.add(mnuFileExit);	
		
		mnuBar.add(mnuFile);
		
		mnuEdit.add(mnuEditCopy);
		mnuEdit.add(mnuEditPaste);
		mnuBar.add(mnuEdit);
		
		
		mnuBar.add(mnuHelp);
		
		
		// Content Menu
		
		Container con=getContentPane();
		con.setLayout(new BorderLayout());
		JPanel pnMainChinh=new JPanel();		
		pnMainChinh.setLayout(new BorderLayout());
		con.add(pnMainChinh,BorderLayout.CENTER);
		
		JPanel pnMain=new JPanel();		
		pnMain.setLayout(new BorderLayout());
		pnMainChinh.add(pnMain,BorderLayout.CENTER);
		
		
		btnThongKe=new JButton("Thống kê");		
		btnBaoCao=new JButton("Báo cáo");	
		btnTraCuu=new JButton("Tra Cứu");
		btnMocThoiGian=new JButton("Mốc thời gian");
		
		toolbar=new JToolBar();
		toolbar.add(btnThongKe); 
		toolbar.add(btnBaoCao);		
		toolbar.add(btnTraCuu);	
		toolbar.add(btnMocThoiGian);	
		
		con.add(toolbar, BorderLayout.NORTH);
		
		//
		//
		//
		//
		
		
		JPanel pnMenu=new JPanel();		
		pnMenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnMain.add(pnMenu,BorderLayout.NORTH);		
				
		
		btnNhapLai=new JButton("Nhập trường hợp vi phạm");
		pnMenu.add(btnNhapLai);
		btnThem=new JButton("Them");
		pnMenu.add(btnThem);
		btnSua=new JButton("Sửa");
		pnMenu.add(btnSua);
		btnXoa=new JButton("Xóa");
		pnMenu.add(btnXoa);
		btnCapNhap=new JButton("Cập nhập");
		pnMenu.add(btnCapNhap);				
		
		
		txtTimKiem=new JTextField();
		txtTimKiem.setPreferredSize(new Dimension(200, 30));
		btnTimKiem=new JButton("Tìm kiếm");
		pnMenu.add(txtTimKiem);
		pnMenu.add(btnTimKiem);
				
		//CHUA CAC THONG TIN NHAP LIEU
		
		
		JPanel pnNhap=new JPanel();
		//pnNhap.setLayout(new BorderLayout());	 //Neu de se xay ra loi chong cheo cac panel
		pnNhap.setPreferredSize(new Dimension(0,500));	
		
		pnMain.add(pnNhap,BorderLayout.CENTER);		
		TitledBorder border2=new TitledBorder(BorderFactory.createLineBorder(Color.GRAY),
				"Cập nhập số liệu");
		pnNhap.setBorder(border2);
		
		
		//Region Panel1
		
		
		JPanel pnNhap1 =new JPanel();		
		pnNhap1.setLayout(new BoxLayout(pnNhap1, BoxLayout.Y_AXIS));		
		pnNhap.add(pnNhap1);
						
		JPanel pnSTT=new JPanel();
		pnSTT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSTT=new JLabel(" STT ");
		txtSTT=new JTextField(8);
		pnSTT.add(lblSTT);
		pnSTT.add(txtSTT);		
		pnNhap1.add(pnSTT);
		
		JPanel pnNgayPhatSinh=new JPanel();
		pnNgayPhatSinh.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayPhatSinh=new JLabel("Ngày phát sinh");
		txtNgayPhatSinh =new JTextField(8);
		pnNgayPhatSinh.add(lblNgayPhatSinh);
		pnNgayPhatSinh.add(txtNgayPhatSinh);
		pnNhap1.add(pnNgayPhatSinh);
		
		JPanel pnThanhPhoHuyen=new JPanel();
		pnThanhPhoHuyen.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblThanhPhoHuyen=new JLabel("Tp/Huyen");
		
		cboHuyen=new JComboBox<>();
		cboHuyen.setPreferredSize(txtNgayPhatSinh.getPreferredSize());				
		pnThanhPhoHuyen.add(lblThanhPhoHuyen);
		pnThanhPhoHuyen.add(cboHuyen);
		pnNhap1.add(pnThanhPhoHuyen);
		
		JPanel pnXa=new JPanel();
		pnXa.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblXa=new JLabel("Xa");
		cboXa=new JComboBox<>();
		cboXa.setPreferredSize(txtNgayPhatSinh.getPreferredSize());		
		pnXa.add(lblXa);
		pnXa.add(cboXa);
		pnNhap1.add(pnXa);
		
		JPanel pnNguoiXuLy=new JPanel();
		pnNguoiXuLy.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNguoiXuLy=new JLabel("Người xử lý");
		txtNguoiXuLy =new JTextField(8);
		pnNguoiXuLy.add(lblNguoiXuLy);
		pnNguoiXuLy.add(txtNguoiXuLy);
		pnNhap1.add(pnNguoiXuLy);
		
		
		JPanel pnDoiXuLy=new JPanel();
		pnDoiXuLy.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDoiXuLy=new JLabel("Đội xử lý");		
		cboDoiXuLy=new JComboBox<>();										// combobox Doi xu ly
		cboDoiXuLy.setPreferredSize(txtNguoiXuLy.getPreferredSize());
		pnDoiXuLy.add(lblDoiXuLy);
		pnDoiXuLy.add(cboDoiXuLy);
		pnNhap1.add(pnDoiXuLy);
		
		JPanel pnTenDuong=new JPanel();
		pnTenDuong.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenDuong=new JLabel(" Tên đường ");			
		cboTuyenDuong=new JComboBox<>();									// ComboBox Tuyen Duong
		cboTuyenDuong.setPreferredSize(txtNguoiXuLy.getPreferredSize());
		pnTenDuong.add(lblTenDuong);
		pnTenDuong.add(cboTuyenDuong);		
		pnNhap1.add(pnTenDuong);
		
		
		JPanel pnLyTrinh=new JPanel();
		pnLyTrinh.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblLyTrinh=new JLabel("Lý trình");
		txtLyTrinh=new JTextField(8);
		pnLyTrinh.add(lblLyTrinh);
		pnLyTrinh.add(txtLyTrinh);		
		pnNhap1.add(pnLyTrinh);
		
		lblSTT.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblThanhPhoHuyen.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblXa.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblNguoiXuLy.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblDoiXuLy.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblTenDuong.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		lblLyTrinh.setPreferredSize(lblNgayPhatSinh.getPreferredSize());
		
		//EndRegion
		
		//Region Panel2
		
		JPanel pnNhap2 =new JPanel();		
		pnNhap2.setLayout(new BoxLayout(pnNhap2, BoxLayout.Y_AXIS));
		pnNhap.add(pnNhap2);
		
				
		
		JPanel pnNguoiViPham=new JPanel();
		pnNguoiViPham.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNguoiViPham=new JLabel("Người vi phạm");
		txtaNguoiViPham =new JTextArea();	
		txtaNguoiViPham.setPreferredSize(new Dimension(90, 60));	

		txtaNguoiViPham.setLineWrap(true);
		txtaNguoiViPham.setWrapStyleWord(true);
		
		pnNguoiViPham.add(lblNguoiViPham);
		pnNguoiViPham.add(txtaNguoiViPham);		
		pnNhap2.add(pnNguoiViPham);
		
		
		JPanel pnNoiDungViPham=new JPanel();
		pnNoiDungViPham.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNoiDungViPham=new JLabel("Nội dung vi phạm");
		txtaNoiDungViPham=new JTextArea();
		txtaNoiDungViPham.setPreferredSize(new Dimension(90, 60));
		txtaNoiDungViPham.setLineWrap(true);
		txtaNoiDungViPham.setWrapStyleWord(true);		
		pnNoiDungViPham.add(lblNoiDungViPham);
		pnNoiDungViPham.add(txtaNoiDungViPham);		
		pnNhap2.add(pnNoiDungViPham);
		
		
		JPanel pnLoaiViPham=new JPanel();
		pnLoaiViPham.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblLoaiViPham=new JLabel("Loại vi phạm");		
		cboLoaiViPham =new JComboBox<>();
	//	cboLoaiViPham.setPreferredSize(new Dimension(90, 20));
		cboLoaiViPham.setPreferredSize(txtLyTrinh.getPreferredSize());
		
		
		pnLoaiViPham.add(lblLoaiViPham);
		pnLoaiViPham.add(cboLoaiViPham);		
		pnNhap2.add(pnLoaiViPham);
		
		JPanel pnNgayLapBBKC=new JPanel();
		pnNgayLapBBKC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayLapBBKC=new JLabel("Ngày lập BBKC");
		txtNgayLapBBKC=new JTextField(8);
		pnNgayLapBBKC.add(lblNgayLapBBKC);
		pnNgayLapBBKC.add(txtNgayLapBBKC);
		pnNhap2.add(pnNgayLapBBKC);
		
		JPanel pnNgayCGBBKC=new JPanel();
		pnNgayCGBBKC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayCGBBKC=new JLabel("Ngày CG BBKC");
		txtNgayCGBBKC=new JTextField(8);
		pnNgayCGBBKC.add(lblNgayCGBBKC);
		pnNgayCGBBKC.add(txtNgayCGBBKC);
		pnNhap2.add(pnNgayCGBBKC);
		
		
		lblNguoiViPham.setPreferredSize(lblNoiDungViPham.getPreferredSize());
		lblLoaiViPham.setPreferredSize(lblNoiDungViPham.getPreferredSize());
		lblNgayLapBBKC.setPreferredSize(lblNoiDungViPham.getPreferredSize());
		lblNgayCGBBKC.setPreferredSize(lblNoiDungViPham.getPreferredSize());
	
		
		
		
		
		//EndRegion 
		
		
		//Region Panel3
		
		JPanel pnNhap3 =new JPanel();		
		pnNhap3.setLayout(new BoxLayout(pnNhap3, BoxLayout.Y_AXIS));
		pnNhap.add(pnNhap3);
				
		
		JPanel pnNgayLapBBVPHC=new JPanel();
		pnNgayLapBBVPHC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayLapBBVPHC=new JLabel("Ngày lập BBVPHC");
		txtNgayLapBBVPHC=new JTextField(8);
		pnNgayLapBBVPHC.add(lblNgayLapBBVPHC);
		pnNgayLapBBVPHC.add(txtNgayLapBBVPHC);
		pnNhap3.add(pnNgayLapBBVPHC);
		
		JPanel pnSoBBVPHC=new JPanel();
		pnSoBBVPHC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoBBVPHC=new JLabel("Số  BBVPHC");
		txtSoBBVPHC=new JTextField(8);
		pnSoBBVPHC.add(lblSoBBVPHC);
		pnSoBBVPHC.add(txtSoBBVPHC);
		pnNhap3.add(pnSoBBVPHC);
		
		
		JPanel pnNgayCGBBVPHC=new JPanel();
		pnNgayCGBBVPHC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayCGBBVPHC=new JLabel("Ngày CG BBVPHC");
		txtNgayCGBBVPHC=new JTextField(8);
		pnNgayCGBBVPHC.add(lblNgayCGBBVPHC);
		pnNgayCGBBVPHC.add(txtNgayCGBBVPHC);
		pnNhap3.add(pnNgayCGBBVPHC);
		
		JPanel pnSoBBCGVPHC=new JPanel();
		pnSoBBCGVPHC.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoBBCGVPHC=new JLabel("Số  BB CG VPHC");
		txtSoBBCGVPHC=new JTextField(8);
		pnSoBBCGVPHC.add(lblSoBBCGVPHC);
		pnSoBBCGVPHC.add(txtSoBBCGVPHC);
		pnNhap3.add(pnSoBBCGVPHC);
		
		
		JPanel pnNgayQuyetDinh=new JPanel();
		pnNgayQuyetDinh.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNgayQuyetDinh=new JLabel("Ngày Quyết định");
		txtNgayQD=new JTextField(8);
		pnNgayQuyetDinh.add(lblNgayQuyetDinh);
		pnNgayQuyetDinh.add(txtNgayQD);
		pnNhap3.add(pnNgayQuyetDinh);

		JPanel pnSoQuyetDinh=new JPanel();
		pnSoQuyetDinh.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoQuyetDinh=new JLabel("Số  Quyết định");
		txtSoQuyetDinh=new JTextField(8);
		pnSoQuyetDinh.add(lblSoQuyetDinh);
		pnSoQuyetDinh.add(txtSoQuyetDinh);
		pnNhap3.add(pnSoQuyetDinh);

		JPanel pnSoTien=new JPanel();
		pnSoTien.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoTien=new JLabel("Số  tiền");
		txtSoTien=new JTextField(8);
		pnSoTien.add(lblSoTien);
		pnSoTien.add(txtSoTien);
		pnNhap3.add(pnSoTien);
		
		lblNgayLapBBVPHC.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblNgayLapBBVPHC.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblSoBBVPHC.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblSoBBCGVPHC.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblNgayQuyetDinh.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblSoQuyetDinh.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		lblSoTien.setPreferredSize(lblNgayCGBBVPHC.getPreferredSize());
		
		
		//EndRegion Panel3
		
		//Region Panel4
		
				JPanel pnNhap4 =new JPanel();		
				pnNhap4.setLayout(new BoxLayout(pnNhap4, BoxLayout.Y_AXIS));
				pnNhap.add(pnNhap4);
				
							
				JPanel pnNgayBBLV=new JPanel();
				pnNgayBBLV.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblNgayBBLV=new JLabel("Ngày Lập BBLV  ");
				txtNgayBBLV=new JTextField(8);
				pnNgayBBLV.add(lblNgayBBLV);
				pnNgayBBLV.add(txtNgayBBLV);
				pnNhap4.add(pnNgayBBLV);
				
				JPanel pnSoBBLV=new JPanel();
				pnSoBBLV.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblSoBBLV=new JLabel("Số  BBLV");
				txtSoBBLV=new JTextField(8);
				pnSoBBLV.add(lblSoBBLV);
				pnSoBBLV.add(txtSoBBLV);
				pnNhap4.add(pnSoBBLV);
		
				
				JPanel pnNgayBBCK=new JPanel();
				pnNgayBBCK.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblNgayBBCK=new JLabel("Ngày Lập BBCK");
				txtNgayBBCK=new JTextField(8);
				pnNgayBBCK.add(lblNgayBBCK);
				pnNgayBBCK.add(txtNgayBBCK);
				pnNhap4.add(pnNgayBBCK);
				
				JPanel pnSoBBCK=new JPanel();
				pnSoBBCK.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblSoBBCK=new JLabel("Số  BBCK");
				txtSoBBCK=new JTextField(8);
				pnSoBBCK.add(lblSoBBCK);
				pnSoBBCK.add(txtSoBBCK);
				pnNhap4.add(pnSoBBCK);
				
				JPanel pnKetQuaXuLy=new JPanel();
				pnKetQuaXuLy.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblKetQuaXuLy=new JLabel("Kết quả xử lý");
				
				txtaKetQuaXuLy=new JTextArea();
				txtaKetQuaXuLy.setPreferredSize(new Dimension(90, 80));
				txtaKetQuaXuLy.setLineWrap(true);
				txtaKetQuaXuLy.setWrapStyleWord(true);					
				pnKetQuaXuLy.add(lblKetQuaXuLy);
				pnKetQuaXuLy.add(txtaKetQuaXuLy);
				pnNhap4.add(pnKetQuaXuLy);
				
				JPanel pnGhiChu=new JPanel();
				pnGhiChu.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblGhiChu=new JLabel("Ghi chú");		
				cboGhiChu=new JComboBox<>();
				cboGhiChu.setPreferredSize(txtSoBBCK.getPreferredSize());
				pnGhiChu.add(lblGhiChu);
				pnGhiChu.add(cboGhiChu);
				pnNhap4.add(pnGhiChu);
				
				lblSoBBLV.setPreferredSize(lblNgayBBLV.getPreferredSize());
				lblNgayBBCK.setPreferredSize(lblNgayBBLV.getPreferredSize());
				lblSoBBCK.setPreferredSize(lblNgayBBLV.getPreferredSize());
				lblKetQuaXuLy.setPreferredSize(lblNgayBBLV.getPreferredSize());
				lblGhiChu.setPreferredSize(lblNgayBBLV.getPreferredSize());
				
				
				
				// EndRegion Panel 4
				
		//Region Panel 5
				
				JPanel pnNhap5 =new JPanel();		
				pnNhap5.setLayout(new BoxLayout(pnNhap5, BoxLayout.Y_AXIS));
				pnNhap.add(pnNhap5);
				
							
				JPanel pnKhongThongKe=new JPanel();
				pnKhongThongKe.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblKhongThongKe=new JLabel("Không thống kê");				
				cboKhongThongKe=new JComboBox<>();
				cboKhongThongKe.setPreferredSize(txtSoBBCK.getPreferredSize());				
				pnKhongThongKe.add(lblKhongThongKe);
				pnKhongThongKe.add(cboKhongThongKe);
				pnNhap5.add(pnKhongThongKe);
				
				JPanel pnNgayThaoDo=new JPanel();
				pnNgayThaoDo.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblNgayThaoDo=new JLabel("Ngày tháo dỡ");
				txtNgayThaoDo=new JTextField(8);
				pnNgayThaoDo.add(lblNgayThaoDo);
				pnNgayThaoDo.add(txtNgayThaoDo);
				pnNhap5.add(pnNgayThaoDo);
				
				
				JPanel pnNgayCuongChe=new JPanel();
				pnNgayCuongChe.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblNgayCuongChe=new JLabel("Ngày cưỡng chế");
				txtNgayCuongChe=new JTextField(8);
				pnNgayCuongChe.add(lblNgayCuongChe);
				pnNgayCuongChe.add(txtNgayCuongChe);
				pnNhap5.add(pnNgayCuongChe);
				
				JPanel pnNgayTuyenTruyen=new JPanel();
				pnNgayTuyenTruyen.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblNgayTuyenTruyen=new JLabel("Ngày Tuyên truyền");
				txtNgayTuyenTruyen=new JTextField(8);
				pnNgayTuyenTruyen.add(lblNgayTuyenTruyen);
				pnNgayTuyenTruyen.add(txtNgayTuyenTruyen);
				pnNhap5.add(pnNgayTuyenTruyen);
				
				JPanel pnSoLanTaiPham=new JPanel();
				pnSoLanTaiPham.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblSoLanTaiPham=new JLabel("Số lần tái phạm");
				txtSoLanTaiPham=new JTextField(8);
				pnSoLanTaiPham.add(lblSoLanTaiPham);
				pnSoLanTaiPham.add(txtSoLanTaiPham);
				pnNhap5.add(pnSoLanTaiPham);
				
				JPanel pnGhiChuKhac=new JPanel();
				pnGhiChuKhac.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel lblGhiChuKhac=new JLabel("Ghi chú khác");				
				txtaGhiChuKhac=new JTextArea();
				txtaGhiChuKhac.setPreferredSize(new Dimension(90, 70));
				txtaGhiChuKhac.setLineWrap(true);
				txtaGhiChuKhac.setWrapStyleWord(true);	
				pnGhiChuKhac.add(lblGhiChuKhac);
				pnGhiChuKhac.add(txtaGhiChuKhac);
				pnNhap5.add(pnGhiChuKhac);
				
				
				lblKhongThongKe.setPreferredSize(lblNgayTuyenTruyen.getPreferredSize());
				lblNgayCuongChe.setPreferredSize(lblNgayTuyenTruyen.getPreferredSize());
				lblNgayThaoDo.setPreferredSize(lblNgayTuyenTruyen.getPreferredSize());
				lblSoLanTaiPham.setPreferredSize(lblNgayTuyenTruyen.getPreferredSize());
				lblGhiChuKhac.setPreferredSize(lblNgayTuyenTruyen.getPreferredSize());
			//EndRegion	
				
				
		// PN CHI TIET HIEN THI THONG TIN CUA VI PHAM (BANG CHI TIET)
		
		JPanel pnThongTinChiTiet=new JPanel();
		pnThongTinChiTiet.setLayout(new BorderLayout());				
				
		pnMain.add(pnThongTinChiTiet,BorderLayout.SOUTH);
				
		TitledBorder border=new TitledBorder(BorderFactory.createLineBorder(Color.GRAY),
				"Thông tin chi tiết");
		pnThongTinChiTiet.setBorder(border);
		
		// CODE CHIA MAN HINH LAM 2 PHAN BANG NHAU
		
			
		JSplitPane sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,pnNhap,pnThongTinChiTiet);
		
		pnMain.add(sp);
		sp.setOneTouchExpandable(true);
		
		
		// Region BANG DUA THONG TIN VAO
		
		dtm = new DefaultTableModel();
		
		
		dtm.addColumn("STT");
		dtm.addColumn("Ngày phát sinh");
		dtm.addColumn("Tp/Huyện");
		dtm.addColumn("Xã");
		
		dtm.addColumn("Người xử lý");
		dtm.addColumn("Đội xử lý");
		dtm.addColumn("Tên đường");
		dtm.addColumn("Lý trình");
		dtm.addColumn("Người vi phạm");
		dtm.addColumn("Nội dung vi phạm");
		dtm.addColumn("Loại vi phạm");
		
		dtm.addColumn("Ngày lập BB kết cấu");
		dtm.addColumn("Ngày chuyển giao BBKC");
		
		dtm.addColumn("Ngày lập BBVPHC");
		dtm.addColumn("Số BBVPHC");
		dtm.addColumn("Ngày chuyển giao BBVPHC");
		dtm.addColumn("Số  BB chuyển giao BBVPHC");
		
		dtm.addColumn("Ngày Quyết định");		
		dtm.addColumn("Số  Quyết định");
		dtm.addColumn("Số  tiền");
		
		dtm.addColumn("Ngày lập BBLV");
		dtm.addColumn("Số BBLV");
		
		dtm.addColumn("Ngày lập BB cam kết");
		dtm.addColumn("Số BB Cam kết");
		
		dtm.addColumn("Kết quả xử lý");
		dtm.addColumn("Ghi chú");
		dtm.addColumn("Không thống kê");
		
		dtm.addColumn("Ngày tháo dỡ");
		dtm.addColumn("Ngày cưỡng chế");
		dtm.addColumn("Ngày tuyên truyền");
		
		dtm.addColumn("Số lần tái phạm");
		dtm.addColumn("Ghi chú khác");
		
		//EndRegion

		// tblChiTietViPham = new JTable(dtm);
		
		// Region TAO NHIEU DONG CHO BANG -VAN DE LA: KICH THUOC BANG BI LECH DI SO VOI BAN DAU
		  TableColumnModel columnModel;
		  tblChiTietViPham = new JTable(dtm){
		  protected JTableHeader createDefaultTableHeader() {
	        
			return new GroupableTableHeader(columnModel);
		  }
	    };
		
	    
	    
		TableColumnModel cm = tblChiTietViPham.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Địa bàn");
	    g_name.add(cm.getColumn(2));
	    g_name.add(cm.getColumn(3));
	    
	    
	    ColumnGroup g_bbkc = new ColumnGroup("BB KẾT CẤU");
	    g_bbkc.add(cm.getColumn(11));
	    g_bbkc.add(cm.getColumn(12));
	   
	    
	    ColumnGroup g_bbvphc = new ColumnGroup("BIÊN BẢN VPHC");
	    g_bbvphc.add(cm.getColumn(13));
	    g_bbvphc.add(cm.getColumn(14));
	    g_bbvphc.add(cm.getColumn(15));
	    g_bbvphc.add(cm.getColumn(16));
	   
	    
	    
	    
	    ColumnGroup g_quyetdinh = new ColumnGroup("QUYẾT ĐỊNH XP");
	    g_quyetdinh.add(cm.getColumn(17));
	    g_quyetdinh.add(cm.getColumn(18));
	    g_quyetdinh.add(cm.getColumn(19));
	    
	    ColumnGroup g_BBLV = new ColumnGroup("BIÊN BẢN LÀM VIỆC");
	    g_BBLV.add(cm.getColumn(20));
	    g_BBLV.add(cm.getColumn(21));
	    
	    ColumnGroup g_bbck = new ColumnGroup("BB CAM KẾT");
	    g_bbck.add(cm.getColumn(22));
	    g_bbck.add(cm.getColumn(23));
	    
	    
	    GroupableTableHeader header = (GroupableTableHeader)tblChiTietViPham.getTableHeader();
	    header.addColumnGroup(g_name);	   
	    header.addColumnGroup(g_bbkc);
	    header.addColumnGroup(g_bbvphc);
	    header.addColumnGroup(g_quyetdinh);
	    header.addColumnGroup(g_BBLV);
	    header.addColumnGroup(g_bbck);
	    
		// EndRegion TAO NHIEU DONG CHO BANG -VAN DE LA: KICH THUOC BANG BI LECH DI SO VOI BAN DAU
	    
	    
		JScrollPane scTable = new JScrollPane(tblChiTietViPham, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	
		pnThongTinChiTiet.add(scTable,BorderLayout.CENTER);
		
		//Region Dinh dang khoang cach cot trong bang
		
		tblChiTietViPham.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblChiTietViPham.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblChiTietViPham.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblChiTietViPham.getColumnModel().getColumn(3).setPreferredWidth(70);
		tblChiTietViPham.getColumnModel().getColumn(4).setPreferredWidth(70);
		tblChiTietViPham.getColumnModel().getColumn(5).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(6).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(7).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(8).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(9).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(10).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(11).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(12).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(13).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(14).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(15).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(16).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(17).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(18).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(19).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(20).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(21).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(22).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(23).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(24).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(25).setPreferredWidth(100);
		tblChiTietViPham.getColumnModel().getColumn(26).setPreferredWidth(100);
		
		//EndRegion
		
	//	tblChiTietViPham.setFillsViewportHeight(true); 
		
		// khong cho doi che do tu thay doi
	
		
		//Start Canh chinh oi dung cua O trong table
		
		DefaultTableCellRenderer centerRender=new DefaultTableCellRenderer();
		 centerRender.setHorizontalAlignment(JLabel.CENTER);
		
		tblChiTietViPham.getColumnModel().getColumn(2).setCellRenderer(centerRender);
		
		
		
		
		// End Canh Chinh
		
		// Start Thiet lap che do Wrap text cho Cell in JTable (su dung class TexAreaRender
		 TableColumnModel cmodel = tblChiTietViPham.getColumnModel(); 
	        TextAreaRenderer textAreaRenderer = new TextAreaRenderer(); 
	        
	        
	     
	        
	        
	        
	        cmodel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer()); 
	        
	        for(int i=1;i<=31;i++)
	        {
	        cmodel.getColumn(i).setCellRenderer(textAreaRenderer); 
	       
	        }
	        
	     // End Thiet lap che do Wrap text cho Cell in JTable (su dung class TexAreaRender
	        
	        // THiet lap hien thi ngay thang cho cac cot trong bang theo dd/MM/yyyy
	        DateRenderer dateRenderer =new DateRenderer();     	       
	        cmodel.getColumn(1).setCellRenderer(dateRenderer);
	        cmodel.getColumn(11).setCellRenderer(dateRenderer);
	        cmodel.getColumn(12).setCellRenderer(dateRenderer);
	        cmodel.getColumn(13).setCellRenderer(dateRenderer);
	        cmodel.getColumn(15).setCellRenderer(dateRenderer);
	        cmodel.getColumn(17).setCellRenderer(dateRenderer);
	        cmodel.getColumn(20).setCellRenderer(dateRenderer);
	        cmodel.getColumn(22).setCellRenderer(dateRenderer);
	        cmodel.getColumn(27).setCellRenderer(dateRenderer);
	        cmodel.getColumn(28).setCellRenderer(dateRenderer);
	        cmodel.getColumn(29).setCellRenderer(dateRenderer);
	       
	        
	     
		
		tblChiTietViPham.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
		
	
		
		// Start tuy chinh Header
		JTableHeader header2 = tblChiTietViPham.getTableHeader();
		header2.setFont(new Font("Dialog", Font.BOLD, 13));
		
		
		
		
		
		// THU THIET LAP SORT CHO HEADER
//		SortButtonRenderer renderer=new SortButtonRenderer();
//		header2.addMouseListener(new HeaderListener(header2,renderer));
//		
//		
//		public Class getColumnClass(int col) {
//	        switch (col) {
//	          case  0: return String.class;
//	          case  1: return Date.class;
//	          case  2: return Integer.class;
//	          case  3: return Boolean.class;
//	          default: return Object.class;
//	        }
//

		
		    header2.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					JOptionPane.showMessageDialog(null, "aaaaaaaaaaaa");
				}
			});
		    
		    
		    
		    
		    
		    
		    
		
		
	//	header.setPreferredSize(new Dimension(tblChiTietViPham.getColumnModel().getTotalColumnWidth(), 37)); // neu de dong nay thi header chay lung tung
		
		
		
		
		// Region Tao Cay truy Cap
		
		JPanel pnTree = new JPanel();

		pnTree.setLayout(new BorderLayout());
		pnMainChinh.add(pnTree);								//	con.add(pnTree);				//sua o day

		root = new DefaultMutableTreeNode("VI PHẠM");
		tree = new JTree(root);

		// THEM CAC Node vao muc

		DefaultMutableTreeNode nodeTuyenDuong = new DefaultMutableTreeNode("TUYẾN ĐƯỜNG");
		root.add(nodeTuyenDuong);
		
		DefaultMutableTreeNode nodeTuyenDuongQuocLo = new DefaultMutableTreeNode("QUỐC LỘ");
		nodeTuyenDuong.add(nodeTuyenDuongQuocLo);
		
		DefaultMutableTreeNode nodeQL24 = new DefaultMutableTreeNode("QL 24");
		nodeTuyenDuongQuocLo.add(nodeQL24);		
		DefaultMutableTreeNode nodeQL40 = new DefaultMutableTreeNode("QL 40");
		nodeTuyenDuongQuocLo.add(nodeQL40);
		DefaultMutableTreeNode nodeQL40B = new DefaultMutableTreeNode("QL 40B");
		nodeTuyenDuongQuocLo.add(nodeQL40B);
		DefaultMutableTreeNode nodeQL14C = new DefaultMutableTreeNode("QL 14C");
		nodeTuyenDuongQuocLo.add(nodeQL14C);

		
		DefaultMutableTreeNode nodeTuyenDuongTinhLo = new DefaultMutableTreeNode("TỈNH LỘ");
		nodeTuyenDuong.add(nodeTuyenDuongTinhLo);
		DefaultMutableTreeNode node671 = new DefaultMutableTreeNode("671");
		nodeTuyenDuongTinhLo.add(node671);
		DefaultMutableTreeNode node672 = new DefaultMutableTreeNode("672");
		nodeTuyenDuongTinhLo.add(node672);
		DefaultMutableTreeNode node673 = new DefaultMutableTreeNode("673");
		nodeTuyenDuongTinhLo.add(node673);
		DefaultMutableTreeNode node674 = new DefaultMutableTreeNode("674");
		nodeTuyenDuongTinhLo.add(node674);
		DefaultMutableTreeNode node675 = new DefaultMutableTreeNode("675");
		nodeTuyenDuongTinhLo.add(node675);
		DefaultMutableTreeNode node676 = new DefaultMutableTreeNode("676");
		nodeTuyenDuongTinhLo.add(node676);
		DefaultMutableTreeNode node677= new DefaultMutableTreeNode("677");
		nodeTuyenDuongTinhLo.add(node677);
		DefaultMutableTreeNode node678 = new DefaultMutableTreeNode("678");
		nodeTuyenDuongTinhLo.add(node678);
		DefaultMutableTreeNode nodeDakKoi = new DefaultMutableTreeNode("Đăk Kôi-Đăk Pxi");
		nodeTuyenDuongTinhLo.add(nodeDakKoi);
		DefaultMutableTreeNode nodeTDC = new DefaultMutableTreeNode("TĐC PleiKrông");
		nodeTuyenDuongTinhLo.add(nodeTDC);
		DefaultMutableTreeNode nodeNHMB = new DefaultMutableTreeNode("NH-MB-TMR-NL");
		nodeTuyenDuongTinhLo.add(nodeNHMB);
		
		// ADD THEO DIA BAN - thanh pho, huyen, xa (tam thoi chua cap nhap) de dinh hinh khung
		
		DefaultMutableTreeNode nodediaban = new DefaultMutableTreeNode("ĐỊA BÀN");
		root.add(nodediaban);

		// TP KON TUM
		
		DefaultMutableTreeNode nodetpkontum = new DefaultMutableTreeNode("Thành phố Kon Tum");
		nodediaban.add(nodetpkontum);		
		DefaultMutableTreeNode nodePhuongTruongChinh = new DefaultMutableTreeNode("Phường Trường Chinh");
		nodetpkontum.add(nodePhuongTruongChinh);
		DefaultMutableTreeNode nodePhuongDuyTan = new DefaultMutableTreeNode("Phường Duy Tân");
		nodetpkontum.add(nodePhuongDuyTan);
		DefaultMutableTreeNode nodePhuongNgoMay = new DefaultMutableTreeNode("Phường Ngô Mây");
		nodetpkontum.add(nodePhuongNgoMay);
		DefaultMutableTreeNode nodeXaDakBla = new DefaultMutableTreeNode("Xã Đắk Blà");
		nodetpkontum.add(nodeXaDakBla);
		DefaultMutableTreeNode nodeXaDakCam = new DefaultMutableTreeNode("Xã Đắk Cấm");
		nodetpkontum.add(nodeXaDakCam);
		DefaultMutableTreeNode nodeXaDakRoWa = new DefaultMutableTreeNode("Xã Đắk Rơ Wa");
		nodetpkontum.add(nodeXaDakRoWa);
		DefaultMutableTreeNode nodeXaChuHreng = new DefaultMutableTreeNode("Xã Chư Hreng");
		nodetpkontum.add(nodeXaChuHreng);
		DefaultMutableTreeNode nodeXaDoanKet = new DefaultMutableTreeNode("Xã Đoàn Kết");
		nodetpkontum.add(nodeXaDoanKet);
		DefaultMutableTreeNode nodeXaIaChim = new DefaultMutableTreeNode("Xã Ia Chim");
		nodetpkontum.add(nodeXaIaChim);
		DefaultMutableTreeNode nodeXaVinhQuang = new DefaultMutableTreeNode("Xã Vinh Quang");
		nodetpkontum.add(nodeXaVinhQuang);
		DefaultMutableTreeNode nodeXaNgocBay = new DefaultMutableTreeNode("Xã Ngọc Bay");
		nodetpkontum.add(nodeXaNgocBay);
		DefaultMutableTreeNode nodeXaKroong = new DefaultMutableTreeNode("Xã Kroong");
		nodetpkontum.add(nodeXaKroong);
		
		
		
		// HUYEN DAK HA
		DefaultMutableTreeNode nodeDakHa = new DefaultMutableTreeNode("Huyện Đăk Hà");
		nodediaban.add(nodeDakHa);		
		DefaultMutableTreeNode nodeHaMon = new DefaultMutableTreeNode("Xa Hà Mòn");
		nodeDakHa.add(nodeHaMon);
		DefaultMutableTreeNode nodeNgocVang = new DefaultMutableTreeNode("Xã Ngok Wang");
		nodeDakHa.add(nodeNgocVang);
		DefaultMutableTreeNode nodeNgocReo = new DefaultMutableTreeNode("Xã Ngok Réo");
		nodeDakHa.add(nodeNgocReo);		
		DefaultMutableTreeNode nodeDakPxi = new DefaultMutableTreeNode("Xã Đắk Pxi");
		nodeDakHa.add(nodeDakPxi);
		DefaultMutableTreeNode nodeDakLong = new DefaultMutableTreeNode("Xã Đăk Long");
		nodeDakHa.add(nodeDakLong);
		DefaultMutableTreeNode nodeDakHring = new DefaultMutableTreeNode("Xã Đắk Hring");
		nodeDakHa.add(nodeDakHring);
		
		//HUYEN DAK TO	
		
		DefaultMutableTreeNode nodeDakTo = new DefaultMutableTreeNode("Huyện Đăk Tô");
		nodediaban.add(nodeDakTo);
		DefaultMutableTreeNode nodeTTDakTo = new DefaultMutableTreeNode("Thị trấn Đắk Tô");
		nodeDakTo.add(nodeTTDakTo);
		DefaultMutableTreeNode nodeXaDakTram = new DefaultMutableTreeNode("Xã Đắk Trăm");
		nodeDakTo.add(nodeXaDakTram);
		DefaultMutableTreeNode nodeXaKonDao = new DefaultMutableTreeNode("Xã Kon Đào");
		nodeDakTo.add(nodeXaKonDao);
		DefaultMutableTreeNode nodeXaNgocTu = new DefaultMutableTreeNode("Xã Ngok Tụ");
		nodeDakTo.add(nodeXaNgocTu);
		DefaultMutableTreeNode nodeXaPoKo = new DefaultMutableTreeNode("Xã Pô Kô");
		nodeDakTo.add(nodeXaPoKo);
		
		
		//HUyen NGOC HOI
		
		DefaultMutableTreeNode nodeNgocHoi = new DefaultMutableTreeNode("Huyện Ngọc Hồi");
		nodediaban.add(nodeNgocHoi);
		DefaultMutableTreeNode nodeTTNgocHoi = new DefaultMutableTreeNode("Thị trấn Ngọc Hồi");
		nodeNgocHoi.add(nodeTTNgocHoi);
		DefaultMutableTreeNode nodeXaDakXu = new DefaultMutableTreeNode("Xã Đắk Xú");
		nodeNgocHoi.add(nodeXaDakXu);
		DefaultMutableTreeNode nodeXaPoY = new DefaultMutableTreeNode("Xã Pờ Y");
		nodeNgocHoi.add(nodeXaPoY);
		DefaultMutableTreeNode nodeXaDakKan = new DefaultMutableTreeNode("Xã Đắk Kan");
		nodeNgocHoi.add(nodeXaDakKan);
		
		// HUYEN DAK GLEI
		DefaultMutableTreeNode nodeDakGlei = new DefaultMutableTreeNode("Huyện Đăk Glei");
		nodediaban.add(nodeDakGlei);
		DefaultMutableTreeNode nodeXaDakMan = new DefaultMutableTreeNode("Xã Đắk Man");
		nodeDakGlei.add(nodeXaDakMan);
		DefaultMutableTreeNode nodeXaDakChoong = new DefaultMutableTreeNode("Xã Đắk Choong");
		nodeDakGlei.add(nodeXaDakChoong);
		DefaultMutableTreeNode nodeXaMuongHoong = new DefaultMutableTreeNode("Xã Mường Hoong");
		nodeDakGlei.add(nodeXaMuongHoong);
		DefaultMutableTreeNode nodeXaNgocLinh = new DefaultMutableTreeNode("Xã Ngọc Linh");
		nodeDakGlei.add(nodeXaNgocLinh);
		
		//HUYEN SA THAY
		DefaultMutableTreeNode nodeSaThay = new DefaultMutableTreeNode("Huyện Sa Thầy");
		nodediaban.add(nodeSaThay);
		DefaultMutableTreeNode nodeTTSaThay = new DefaultMutableTreeNode("Thị Trấn Sa Thầy");
		nodeSaThay.add(nodeTTSaThay);
		DefaultMutableTreeNode nodeXaRoKoi = new DefaultMutableTreeNode("Xã Rờ Kơi");
		nodeSaThay.add(nodeXaRoKoi);
		DefaultMutableTreeNode nodeXaMoRai = new DefaultMutableTreeNode("Xã Mô Rai");
		nodeSaThay.add(nodeXaMoRai);
		DefaultMutableTreeNode nodeXaSaSon = new DefaultMutableTreeNode("Xã Sa Sơn");
		nodeSaThay.add(nodeXaSaSon);
		DefaultMutableTreeNode nodeXaSaNhon = new DefaultMutableTreeNode("Xã Sa Nhơn");
		nodeSaThay.add(nodeXaSaNhon);
		DefaultMutableTreeNode nodeXaSaBinh = new DefaultMutableTreeNode("Xã Sa Bình");
		nodeSaThay.add(nodeXaSaBinh);
		DefaultMutableTreeNode nodeXaSaNghia = new DefaultMutableTreeNode("Xã Sa Nghĩa");
		nodeSaThay.add(nodeXaSaNghia);
		DefaultMutableTreeNode nodeXaHoMoong = new DefaultMutableTreeNode("Xã Hơ Moong");
		nodeSaThay.add(nodeXaHoMoong);
		
		
		
		// HUYEN KON RAY
		DefaultMutableTreeNode nodeKonRay = new DefaultMutableTreeNode("Huyện Kon Rẫy");
		nodediaban.add(nodeKonRay);
		DefaultMutableTreeNode nodeTTDakRve = new DefaultMutableTreeNode("Thị trấn Đắk Rve");
		nodeKonRay.add(nodeTTDakRve);
		DefaultMutableTreeNode nodeXaTanLap = new DefaultMutableTreeNode("Xã Tân Lập ");
		nodeKonRay.add(nodeXaTanLap);
		DefaultMutableTreeNode nodeXaDakRuong = new DefaultMutableTreeNode("Xã Đắk Ruồng");
		nodeKonRay.add(nodeXaDakRuong);
		DefaultMutableTreeNode nodeXaDakToRe = new DefaultMutableTreeNode("Xã Đắk Tờ Re");
		nodeKonRay.add(nodeXaDakToRe);
		DefaultMutableTreeNode nodeXaDakToLung = new DefaultMutableTreeNode("Xã Đắk Tơ Lung");
		nodeKonRay.add(nodeXaDakToLung);
		DefaultMutableTreeNode nodeXaDakKoi = new DefaultMutableTreeNode("Xã Đắk Kôi");
		nodeKonRay.add(nodeXaDakKoi);
		
		
		//HUYEN TU MO RONG
		DefaultMutableTreeNode nodeTuMoRong = new DefaultMutableTreeNode("Huyện Tu Mơ Rông");
		nodediaban.add(nodeTuMoRong);
		DefaultMutableTreeNode nodeXaNgocLay = new DefaultMutableTreeNode("Xã Ngọc Lây");
		nodeTuMoRong.add(nodeXaNgocLay);
		DefaultMutableTreeNode nodeXaTuMoRong = new DefaultMutableTreeNode("Xã Tu Mơ Rông");
		nodeTuMoRong.add(nodeXaTuMoRong);
		DefaultMutableTreeNode nodeXaDakHa = new DefaultMutableTreeNode("Xã Đắk Hà");
		nodeTuMoRong.add(nodeXaDakHa);
		DefaultMutableTreeNode nodeXaTeXang = new DefaultMutableTreeNode("Xã Tê Xăng");
		nodeTuMoRong.add(nodeXaTeXang);
		DefaultMutableTreeNode nodeXaMangRi = new DefaultMutableTreeNode("Xã Măng Ri");
		nodeTuMoRong.add(nodeXaMangRi);
		DefaultMutableTreeNode nodeXaDakToKan = new DefaultMutableTreeNode("Xã Đắk Tơ Kan");
		nodeTuMoRong.add(nodeXaDakToKan);
		DefaultMutableTreeNode nodeXaDakRoOng = new DefaultMutableTreeNode("Xã Đắk Rơ Ông");
		nodeTuMoRong.add(nodeXaDakRoOng);
		DefaultMutableTreeNode nodeXaDakSao = new DefaultMutableTreeNode("Xã Đắk Sao");
		nodeTuMoRong.add(nodeXaDakSao);
		DefaultMutableTreeNode nodeXaDakNa = new DefaultMutableTreeNode("Xã Đắk Na");
		nodeTuMoRong.add(nodeXaDakNa);
		
		//HUYEN KONPLONG
		DefaultMutableTreeNode nodeKonPlong = new DefaultMutableTreeNode("Huyện Kon Plông");
		nodediaban.add(nodeKonPlong);
		DefaultMutableTreeNode nodeXaPoE = new DefaultMutableTreeNode("Xã Pờ Ê");
		nodeKonPlong.add(nodeXaPoE);
		DefaultMutableTreeNode nodeXaHieu = new DefaultMutableTreeNode("Xã Hiếu");
		nodeKonPlong.add(nodeXaHieu);
		DefaultMutableTreeNode nodeXaDakLong = new DefaultMutableTreeNode("Xã Đắk Long");
		nodeKonPlong.add(nodeXaDakLong);
		DefaultMutableTreeNode nodeXaMangCanh = new DefaultMutableTreeNode("Xã Măng Cành");
		nodeKonPlong.add(nodeXaMangCanh);
		DefaultMutableTreeNode nodeXaDakTang = new DefaultMutableTreeNode("Xã Đắk Tăng");
		nodeKonPlong.add(nodeXaDakTang);
		DefaultMutableTreeNode nodeXaDakRing = new DefaultMutableTreeNode("Xã Đắk Ring");
		nodeKonPlong.add(nodeXaDakRing);
		
		
		
		
		
		//HUYEN IA HDRAI
		DefaultMutableTreeNode nodeIaHrai = new DefaultMutableTreeNode("Huyện Ia HDrai");
		nodediaban.add(nodeIaHrai);
		DefaultMutableTreeNode nodeIaDom = new DefaultMutableTreeNode("Xã Ia dom");
		nodeIaHrai.add(nodeIaDom);
		DefaultMutableTreeNode nodeIaToi = new DefaultMutableTreeNode("Xã Ia Tơi");
		nodeIaHrai.add(nodeIaToi);
		
		// CAC HUYEN KHAC LAM TUONG TU VA CAC XA KHAC LAM TUONG TU NHU TREN
		
			
		// Start Tu Dong expand the node cau cay thu muc
		for (int i = 0; i < tree.getRowCount(); i++) {
		    tree.expandRow(i);
		}
		
		//End Tu Dong expand the node cau cay thu muc
		
		pnTree.setPreferredSize(new Dimension(180, 0));

		JScrollPane sc2 = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pnTree.add(sc2, BorderLayout.CENTER);

				
		JSplitPane sp2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnTree, pnMain);
		con.add(sp2, BorderLayout.CENTER);

		sp2.setOneTouchExpandable(true);
		
	

		// EndRegion Tao Cay truy Cap
		
		
		//Region Hien Thi Thong Tin tong quat nhu Ngay h He Thong, Nguoi Dang nhap, Tong so Vu
		
				JPanel pnThongTinPhu=new JPanel();		
				pnThongTinPhu.setLayout(new FlowLayout(FlowLayout.LEFT));
								
				con.add(pnThongTinPhu, BorderLayout.SOUTH);
	
					Date date = new Date();
			    
			      pnThongTinPhu.add(lblThongTinTongQuat);
			      
			

			      
			     
			   
			    //  dtm.getRowCount()
			   
			     // tblChiTietViPham.getRowCount()
			      
			      
				//EndRegion	
		
		
		
	}
	
	
	
	public void addEvent()
	{
		mnuFileExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		mnuFileNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "ban vua tao file moi");
			
			//	UpdateExcel.capNhap("tien","anh");  // da hoat dong
				
				XuatBaoCao();
				
			}
				
			
		});
		
		
		tblChiTietViPham.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1){
					xuLyNhap(); // xoa cac du lieu hien co cua cac jtextfile va combobox de hien thi cho dung
				int row=tblChiTietViPham.getSelectedRow();
				if(row==-1)return;
				String ma=tblChiTietViPham.getValueAt(row,0)+"";
				hienThiChiTietTHVP(ma);
				}
			
			}
		});
		
		btnThem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xuLyLuu();
				
			}
		});
		
		btnXoa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xuLyXoa();
				
			}
		});
		
		btnCapNhap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
			hienThiDSVPHLATDB();
			}
		});
		
		
		btnNhapLai.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xuLyNhap();
				
				
			}
		});
		
		tree.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				Object obj = tree.getLastSelectedPathComponent();
				DefaultMutableTreeNode nodeSelected = (DefaultMutableTreeNode) obj;
				
				//Region su kien lay danh sach vi pham theo duong, dia ban
				
				
				switch(nodeSelected.toString())
				{
				case "VI PHẠM":
					hienThiDSVPHLATDB();
					break;
					
				case "TUYẾN ĐƯỜNG":
					hienThiDSVPHLATDB();
					break;
					
				case "ĐỊA BÀN":
					hienThiDSVPHLATDB();
					break;
					
					case "TỈNH LỘ":
					
						hienThiDSVPHLATDBTinhLo();
						
						System.out.println(laySTTCuoiCung());
					
					break;	
					
				case "QL 24":					
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","QL24");
					break;
					
				case "QL 40":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","40");
					break;
					
				case "QL 40B":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","40B");
					break;	
					
				case "QL 14C":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","14C");
					break;	
					
				case "QUỐC LỘ":
					hienThiDSVPHLATDBQuocLo("QL24","14C","40B","40");
					break;	
					

					
				case "671":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","671");
					break;	
				case "672":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","672");
					break;	
				
				case "673":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","673");
					break;	
				case "674":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","674");
					break;	
				case "675":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","675");
					break;	
				case "676":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","676");
					break;	
				case "677":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","677");
					break;						
				case "678":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","678");
					break;	
					
				case "Đăk Kôi-Đăk Pxi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","ĐăkKôi- Đăk pxi");
					break;	
				case "TĐC PleiKrông":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","Plei Krong");
					break;	
				case "NH-MB-TMR-NL":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Duong","NH-MB-TMR-NL");
					break;	
					
				case "Thành phố Kon Tum":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","TP Kon Tum");
					break;		
					
				case "Huyện Đăk Hà":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Đắk Hà");
					break;	
					
				case "Huyện Đăk Glei":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Đắk Glei");
					break;	
				case "Huyện Đăk Tô":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Đắk Tô");
					break;	
				case "Huyện Ia HDraiL":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Ia H'Drai");
					break;	
				case "Huyện Kon Plông":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Kon Plông");
					break;	
					
				case "Huyện Kon Rẫy":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Kon Rẫy");
					break;	
				case "Huyện Ngọc Hồi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Ngọc Hồi");
					break;	
				case "Huyện Sa Thầy":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Sa Thầy");
					break;	
				case "Huyện Tu Mơ Rông":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Huyen","Tu Mơ Rông");					
					break;	
								
					//Tpkon tum
					
				case "Phường Trường Chinh":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Phường Trường Chinh");
				break;			
					
				case "Phường Duy Tân":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Phường Duy Tân");
				break;		
				case "Phường Ngô Mây":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Phường Ngô Mây");
				break;		
				case "Xã Đắk Blà":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Blà");
				break;		
				case "Xã Đắk Cấm":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Cấm");
				break;		
				case "Xã Đắk Rơ Wa":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Rơ Wa");
				break;		
					
				case "Xã Chư Hreng":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Chư Hreng");
				break;	
				case "Xã Đoàn Kết":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đoàn Kết");
				break;	
				case "Xã Ia Chim":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ia Chim");
				break;	
				case "Xã Vinh Quang":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Vinh Quang");
				break;	
					
				case "Xã Ngọc Bay":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngọc Bay");
				break;	
				
				case "Xã Kroong":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Kroong");
				break;	
					
				// sa thay	
					
				case "Thị Trấn Sa Thầy":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Thị Trấn Sa Thầy");
				break;	
				
				case "Xã Rờ Kơi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Rờ Kơi");
				break;	
				case "Xã Mô Rai":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Mô Rai");
				break;	
				case "Xã Sa Sơn":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Sa Sơn");
				break;	
				case "Xã Sa Nhơn":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Sa Nhơn");
				break;	
				case "Xã Sa Bình":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Sa Bình");
				break;	
				case "Xã Sa Nghĩa ":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Sa Nghĩa ");
				break;	
				case "Xã Hơ Moong":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Hơ Moong");
				break;	
				
				
				//hyen ia hdrai
				case "Xã Ia dom":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ia dom");
				break;					
			
				case "Xã Ia Tơi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ia Tơi");
				break;	
				
				// kon ray
				case "Thị trấn Đắk Rve":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Thị trấn Đắk Rve");
				break;	
				case "Xã Tân Lập ":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Tân Lập ");
				break;	
				case "Xã Đắk Ruồng":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Ruồng");
				break;	
				
				
				case "Xã Đắk Tờ Re":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Tờ Re");
				break;	
				
				case "Xã Đắk Tơ Lung":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Tơ Lung");
				break;				
				
				case "Xã Đắk Kôi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Kôi");
				break;	
				
				// dak glei
				case "Xã Đắk Man":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Man");
				break;	
				case "Xã Đắk Choong":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Choong");
				break;	
				case "Xã Mường Hoong":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Mường Hoong");
				break;	
				case "Xã Ngọc Linh":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngọc Linh");
				break;	
				
				// huyen kon plong
				case "Xã Pờ Ê":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Pờ Ê");
				break;	
				case "Xã Hiếu":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Hiếu");
				break;	
				case "Xã Đắk Long":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Long");
				break;	
				
				case "Xã Măng Cành":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Măng Cành");
				break;	
				case "Xã Đắk Tăng":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Tăng");
				break;	
				case "Xã Đắk Ring":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Ring");
				break;	
					
				// huyen dak ha
				case "Xã Hà Mòn":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Hà Mòn");
				break;	
				
				case "Xã Ngok Wang":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngok Wang");
				break;	
				case "Xã Ngok Réo":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngok Réo");
				break;	
				case "Xã Đắk Pxi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Pxi");
				break;	
				case "Xã Đăk Long":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đăk Long");
				break;	
				case "Xã Đắk Hring":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Hring");
				break;	
				
				//dak to
				case "Thị trấn Đắk Tô":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Thị trấn Đắk Tô");
				break;	
				case "Xã Đắk Trăm":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Trăm");
				break;	
				case "":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","");
				break;	
				case "Xã Kon Đào":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Kon Đào");
				break;	
				case "Xã Ngok Tụ":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngok Tụ");
				break;	
				case "Xã Pô Kô":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Pô Kô");
				break;	
				
				//tu mo rong
				case "Xã Ngọc Lây":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Ngọc Lây");
				break;		
				case "Xã Tu Mơ Rông":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Tu Mơ Rông");
				break;			
					
				case "Xã Đắk Hà":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Hà");
				break;		
				
				case "Xã Tê Xăng":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Tê Xăng");
				break;		
				case "Xã Măng Ri":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Măng Ri");
				break;		
				case "Xã Đắk Tơ Kan":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Tơ Kan");
				break;		
				case "Xã Đắk Rơ Ông":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Rơ Ông");
				break;		
				case "Xã Đắk Sao":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Sao");
				break;		
				case "Xã Đắk Na":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Na");
				break;	
				
				//ngoc hoi
				case "Thị trấn Ngọc Hồi":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Thị trấn Ngọc Hồi");
				break;		
				case "Xã Đắk Xú":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Xú");
				break;	
					
				case "Xã Pờ Y":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Pờ Y");
				break;	
				case "Xã Đắk Kan":
					hienThiDSVPHLATDBTheoDuongHuyenXa("Xa","Xã Đắk Kan");
				break;	
				
				//EndRegion khi nhan nut vao
				
//				default:
//					Vector<Xa> xa=XaService.LayDSXa();
//					if(xa.contains(nodeSelected))
//						hienThiDSVPHLATDBTheoDuongHuyenXa("Xa",nodeSelected.toString());
//					break;
////				
				
				}
				
			}
		});
		
		
	cboHuyen.addItemListener(new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			LayDSxaTheoHuyen();
		}
	});
		
	
	
	
		
	}
	
	protected void XuatBaoCao() {
		
		// ghien cuu de dua du lieu vao phan nay		
		
		
		//Vector<TruongHopViPham> readingStudents = thvp;		// tao 1 truong du lieu san de chen vao khi cap nhap
		
		String outputFileName = "BaoCao/TONGHOP2.xls";

		boolean isWrited = UpdateExcel.writeTHVP( thvp, outputFileName);

		if (isWrited) {

			System.out.println("Xuat thanh cong");
		} else {

			System.out.println("Xuat That bai");
		}
	
		
		
	}

	protected void xuLyNhap() {
	
		stt=laySTTCuoiCung()+1;	
		
	txtSTT.setText(Integer.toString(stt));				
		
		
	txtNgayPhatSinh.setText("");
	
	cboTuyenDuong.setSelectedIndex(-1);				
	cboHuyen.setSelectedIndex(-1);			
	cboXa.setSelectedIndex(-1);
		
	
	txtNguoiXuLy.setText("");	
	
	
	cboDoiXuLy.setSelectedIndex(-1);
		
	
	
	txtLyTrinh.setText("");
	txtaNguoiViPham.setText("");
	txtaNoiDungViPham.setText("");	
				
	cboLoaiViPham.setSelectedIndex(-1);		
	
	txtNgayLapBBKC.setText("");			
	txtNgayCGBBKC.setText("");	
	txtNgayLapBBVPHC.setText("");	
	txtSoBBVPHC.setText("");	
	txtNgayCGBBVPHC.setText("");	
	txtSoBBCGVPHC.setText("");
	
	txtNgayQD.setText("");
	txtSoQuyetDinh.setText("");
	txtSoTien.setText("");
	
	txtNgayBBLV.setText("");			
	txtSoBBLV.setText("");
	
	txtNgayBBCK.setText("");			
	txtSoBBCK.setText("");	
	txtaKetQuaXuLy.setText("");
		
	cboGhiChu.setSelectedIndex(-1);
		
	txtNgayThaoDo.setText("");		
	
	txtNgayCuongChe.setText("");	
	txtNgayTuyenTruyen.setText("");		
	
	txtSoLanTaiPham.setText(null);
	
	txtaGhiChuKhac.setText("");	
			
	cboKhongThongKe.setSelectedIndex(-1);
		
	
		
	}

	protected void xuLyLuu() {
		
		stt=laySTTCuoiCung()+1; // khai bao o day de tranh truong hop loi do conect 2 lan se bao loi
		
		if(kiemTraMaTonTai(txtSTT.getText())==true)
		{
			
			int ret=JOptionPane.showConfirmDialog(null, "Mã ["+txtSTT.getText()+"] đã tồn tại có muốn cập nhập không","Thông báo",
					JOptionPane.YES_NO_OPTION);
			
			if(ret==JOptionPane.NO_OPTION) return;
			try
			{
			
				String sql="update NHAPTHONGTIN set NgayPhatSinh=?,Huyen=?,Xa=?,NguoiXuLy=?,"
						+ "DoiXuLy=?,TenDuong=?,LyTrinh=?,NguoiViPham=?,NoiDungViPham=?,LoaiViPham=?,"
						+ "NgayLapBBKC=?,NgayChuyenGiaoBBKC=?,NgayLapBBVPHC=?,SoBBVPHC=?,"
						+ "NgayChuyenGiaoBBVPHC=?,SoBBChuyenGiao=?,NgayQuyetDinh=?,SoQuyetDinh=?,SoTien=?,"
						+ "NgayBBLV=?,SoBBLV=?,NgayBBCamKet=?,SoBBCamKet=?,KetQuaXuLy=?,GhiChu=?,"
						+ "KhongThongKe=?,NgayThaoDo=?,NgayCuongChe=?,NgayTuyenTruyen=?,SoLanTaiPham=?,GhiChuKhac=? where STT=?";
				
				
				preStatement=conn.prepareStatement(sql);				
			
				// kiem tra neu ngay thang null --null con neu khong null kiem tra ngay thang co hop le hay khong
				if(txtNgayPhatSinh.getText().equals(null)||txtNgayPhatSinh.getText().toString().equals(""))
				{
					preStatement.setString(1,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayPhatSinh.getText())){
					
					preStatement.setDate(1,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayPhatSinh.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra ngày phát sinh \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				// them dieu kien kiem tra
				//Neu khong chon thi hien thong bao va tra ve
				if(cboHuyen.getSelectedIndex()!=-1)
				{
				preStatement.setString(2, cboHuyen.getSelectedItem().toString());	
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Tên Huyện/Thành phố");
					
				return;
				}
				
								
				
				if(cboXa.getSelectedIndex()!=-1)
				{
					preStatement.setString(3, cboXa.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Tên Xã");
					
				return;
				}
				
				
				preStatement.setString(4, txtNguoiXuLy.getText());
				
						
				
				if(cboDoiXuLy.getSelectedIndex()!=-1)
				{
					preStatement.setString(5, cboDoiXuLy.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Đội xử lý");
					
				return;
				}
				
				
				
				
				if(cboTuyenDuong.getSelectedIndex()!=-1)
				{
					preStatement.setString(6, cboTuyenDuong.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Tuyến đường");
					
				return;
				}
				
				
				preStatement.setString(7, txtLyTrinh.getText());
				preStatement.setString(8, txtaNguoiViPham.getText());
				preStatement.setString(9, txtaNoiDungViPham.getText());
				
				
				
				if(cboLoaiViPham.getSelectedIndex()!=-1)
				{
					preStatement.setString(10, cboLoaiViPham.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Loại vi phạm");
					
				return;
				}
				
				
				// preStatement.setDate(11,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBKC.getText()).getTime()));
				
				if(txtNgayLapBBKC.getText().equals(null)||txtNgayLapBBKC.getText().toString().equals(""))
				{
					preStatement.setString(11,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayLapBBKC.getText())){
					
					preStatement.setDate(11,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBKC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra NgayLapBBKC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				
				
				
				//preStatement.setDate(12,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBKC.getText()).getTime()));
				if(txtNgayCGBBKC.getText().equals(null)||txtNgayCGBBKC.getText().toString().equals(""))
				{
					preStatement.setString(12,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCGBBKC.getText())){
					
					preStatement.setDate(12,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBKC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra NgayCGBBKC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				
				
				
			//	preStatement.setDate(13,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBVPHC.getText()).getTime()));
				if(txtNgayLapBBVPHC.getText().equals(null)||txtNgayLapBBVPHC.getText().toString().equals(""))
				{
					preStatement.setString(13,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayLapBBVPHC.getText())){
					
					preStatement.setDate(13,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBVPHC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày lập BBVPHC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				preStatement.setString(14, txtSoBBVPHC.getText());
				
				//preStatement.setDate(15,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBVPHC.getText()).getTime()));
				if(txtNgayCGBBVPHC.getText().equals(null)||txtNgayCGBBVPHC.getText().toString().equals(""))
				{
					preStatement.setString(15,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCGBBVPHC.getText())){
					
					preStatement.setDate(15,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBVPHC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Chuyển giao BBVPHC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				preStatement.setString(16, txtSoBBCGVPHC.getText());
			//	preStatement.setDate(17,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayQD.getText()).getTime()));
				if(txtNgayQD.getText().equals(null)||txtNgayQD.getText().toString().equals(""))
				{
					preStatement.setString(17,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayQD.getText())){
					
					preStatement.setDate(17,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayQD.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Quyết định \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				preStatement.setString(18, txtSoQuyetDinh.getText());
				preStatement.setString(19, txtSoTien.getText());
				
			//	preStatement.setDate(20,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBLV.getText()).getTime()));
				if(txtNgayBBLV.getText().equals(null)||txtNgayBBLV.getText().toString().equals(""))
				{
					preStatement.setString(20,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayBBLV.getText())){
					
					preStatement.setDate(20,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBLV.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày BBLV \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				preStatement.setString(21, txtSoBBLV.getText());
		//		preStatement.setDate(22,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBCK.getText()).getTime()));
				if(txtNgayBBCK.getText().equals(null)||txtNgayBBCK.getText().toString().equals(""))
				{
					preStatement.setString(22,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayBBCK.getText())){
					
					preStatement.setDate(22,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBCK.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày BB Cam kết \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
				preStatement.setString(23, txtSoBBCK.getText());
				preStatement.setString(24, txtaKetQuaXuLy.getText());
				
				
				
				if(cboGhiChu.getSelectedIndex()!=-1)
				{
					preStatement.setString(25, cboGhiChu.getSelectedItem().toString());
				}
				else 
				{
					preStatement.setString(25, null);
					
				
				}
								
				
				if(cboKhongThongKe.getSelectedIndex()!=-1)
				{
					preStatement.setString(26, cboKhongThongKe.getSelectedItem().toString());
				}
				else 
				{
					preStatement.setString(26, null);
					
				
				}
				
		//		preStatement.setDate(27,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayThaoDo.getText()).getTime()));
				if(txtNgayThaoDo.getText().equals(null)||txtNgayThaoDo.getText().toString().equals(""))
				{
					preStatement.setString(27,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayThaoDo.getText())){
					
					preStatement.setDate(27,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayThaoDo.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Tháo dỡ \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
			//	preStatement.setDate(28,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCuongChe.getText()).getTime()));
				if(txtNgayCuongChe.getText().equals(null)||txtNgayCuongChe.getText().toString().equals(""))
				{
					preStatement.setString(28,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCuongChe.getText())){
					
					preStatement.setDate(28,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCuongChe.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Cưỡng chế \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
				
			//	preStatement.setDate(29,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayTuyenTruyen.getText()).getTime()));
				if(txtNgayTuyenTruyen.getText().equals(null)||txtNgayTuyenTruyen.getText().toString().equals(""))
				{
					preStatement.setString(29,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayTuyenTruyen.getText())){
					
					preStatement.setDate(29,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayTuyenTruyen.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Tuyên truyền \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}
				
								
				if(txtSoLanTaiPham.getText().toString().equals(null)||txtSoLanTaiPham.getText().toString().equals(""))
				{
					preStatement.setInt(30, 0);
				}
				else 
				{
					
					preStatement.setInt(30, Integer.parseInt(txtSoLanTaiPham.getText()));
				}
				
				
				
				preStatement.setString(31, txtaGhiChuKhac.getText());
				
				preStatement.setString(32, txtSTT.getText());
				int x=preStatement.executeUpdate();
				if(x>0)
				{
					hienThiDSVPHLATDB();
					
				}
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			
			
		}
		else
		{
			try
			{
				String sql="insert into NHAPTHONGTIN values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				preStatement=conn.prepareStatement(sql);				
				
			//	preStatement.setString(1, txtSTT.getText());	
				
		//		System.out.println(laySTTCuoiCung()+1);
				preStatement.setString(1, Integer.toString(stt));	
				
			//	preStatement.setDate(2,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayPhatSinh.getText()).getTime()));
				if(txtNgayPhatSinh.getText().equals(null)||txtNgayPhatSinh.getText().toString().equals(""))
				{
					preStatement.setString(2,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayPhatSinh.getText())){
					
					preStatement.setDate(2,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayPhatSinh.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Phát sinh \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				if(cboHuyen.getSelectedIndex()!=-1)
				{
				
					preStatement.setString(3, cboHuyen.getSelectedItem().toString());	
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Địa bàn TH/Huyện");
					
				return;
				}
				
				
				
				if(cboXa.getSelectedIndex()!=-1)
				{
					preStatement.setString(4, cboXa.getSelectedItem().toString());	
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Địa bàn Xã");
					
				return;
				}
				
				preStatement.setString(5, txtNguoiXuLy.getText());
				
				
				if(cboDoiXuLy.getSelectedIndex()!=-1)
				{
					preStatement.setString(6, cboDoiXuLy.getSelectedItem().toString());	
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Đội xử lý");
					
				return;
				}
				
				
				if(cboTuyenDuong.getSelectedIndex()!=-1)
				{
					preStatement.setString(7, cboTuyenDuong.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Tuyến đường");
					
				return;
				}
				
				
				preStatement.setString(8, txtLyTrinh.getText());
				preStatement.setString(9, txtaNguoiViPham.getText());
				preStatement.setString(10, txtaNoiDungViPham.getText());
				
				
				if(cboLoaiViPham.getSelectedIndex()!=-1)
				{
					preStatement.setString(11, cboLoaiViPham.getSelectedItem().toString());
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Chưa nhập Loại vi phạm");
					
				return;
				}
				
				
				
			//	preStatement.setDate(12,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBKC.getText()).getTime()));
				if(txtNgayLapBBKC.getText().equals(null)||txtNgayLapBBKC.getText().toString().equals(""))
				{
					preStatement.setString(12,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayLapBBKC.getText())){
					
					preStatement.setDate(12,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBKC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Lập BBKC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
			//	preStatement.setDate(13,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBKC.getText()).getTime()));
				if(txtNgayCGBBKC.getText().equals(null)||txtNgayCGBBKC.getText().toString().equals(""))
				{
					preStatement.setString(13,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCGBBKC.getText())){
					
					preStatement.setDate(13,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBKC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày CG BBKC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
		//		preStatement.setDate(14,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBVPHC.getText()).getTime()));
				if(txtNgayLapBBVPHC.getText().equals(null)||txtNgayLapBBVPHC.getText().toString().equals(""))
				{
					preStatement.setString(14,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayLapBBVPHC.getText())){
					
					preStatement.setDate(14,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayLapBBVPHC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Lập BBVPHC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				preStatement.setString(15, txtSoBBVPHC.getText());
				
		//		preStatement.setDate(16,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBVPHC.getText()).getTime()));
				if(txtNgayCGBBVPHC.getText().equals(null)||txtNgayCGBBVPHC.getText().toString().equals(""))
				{
					preStatement.setString(16,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCGBBVPHC.getText())){
					
					preStatement.setDate(16,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCGBBVPHC.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày CG BBVPHC \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				preStatement.setString(17, txtSoBBCGVPHC.getText());
		//		preStatement.setDate(18,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayQD.getText()).getTime()));
				if(txtNgayQD.getText().equals(null)||txtNgayQD.getText().toString().equals(""))
				{
					preStatement.setString(18,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayQD.getText())){
					
					preStatement.setDate(18,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayQD.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Quyết định \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				preStatement.setString(19, txtSoQuyetDinh.getText());
				preStatement.setString(20, txtSoTien.getText());
				
		//		preStatement.setDate(21,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBLV.getText()).getTime()));
				if(txtNgayBBLV.getText().equals(null)||txtNgayBBLV.getText().toString().equals(""))
				{
					preStatement.setString(21,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayBBLV.getText())){
					
					preStatement.setDate(21,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBLV.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày BBLV \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				preStatement.setString(22, txtSoBBLV.getText());
			//	preStatement.setDate(23,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBCK.getText()).getTime()));
				if(txtNgayBBCK.getText().equals(null)||txtNgayBBCK.getText().toString().equals(""))
				{
					preStatement.setString(23,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayBBCK.getText())){
					
					preStatement.setDate(23,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayBBCK.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày BBCK \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				preStatement.setString(24, txtSoBBCK.getText());
				preStatement.setString(25, txtaKetQuaXuLy.getText());
				
				
				if(cboKhongThongKe.getSelectedIndex()!=-1)
				{
					preStatement.setString(26, cboGhiChu.getSelectedItem().toString());
				}
				else 
				{
					preStatement.setString(26, null);
					
				
				}
				
				
				if(cboKhongThongKe.getSelectedIndex()!=-1)
				{
					preStatement.setString(27, cboKhongThongKe.getSelectedItem().toString());
				}
				else 
				{
					preStatement.setString(27, null);
					
				
				}
				
		//		preStatement.setDate(28,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayThaoDo.getText()).getTime()));
				if(txtNgayThaoDo.getText().equals(null)||txtNgayThaoDo.getText().toString().equals(""))
				{
					preStatement.setString(28,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayThaoDo.getText())){
					
					preStatement.setDate(28,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayThaoDo.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Thao dỡ \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
		//		preStatement.setDate(29,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCuongChe.getText()).getTime()));
				if(txtNgayCuongChe.getText().equals(null)||txtNgayCuongChe.getText().toString().equals(""))
				{
					preStatement.setString(29,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayCuongChe.getText())){
					
					preStatement.setDate(29,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayCuongChe.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Cưỡng chế \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
		//		preStatement.setDate(30,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayTuyenTruyen.getText()).getTime()));
				if(txtNgayTuyenTruyen.getText().equals(null)||txtNgayTuyenTruyen.getText().toString().equals(""))
				{
					preStatement.setString(30,null);
				}
				
				else if (KiemTraDuLieuDauVao.isValidDate(txtNgayTuyenTruyen.getText())){
					
					preStatement.setDate(30,new java.sql.Date(sdf_ddMMyyyy.parse(txtNgayTuyenTruyen.getText()).getTime()));
					
					}
				
				else {
					JOptionPane.showMessageDialog(null, "Kiểm tra Ngày Tuyên truyền \n Nhập định dạng  (ngày/tháng/năm)");
					return;
				}		
				
				
				if(txtSoLanTaiPham.getText().toString().equals(null)||txtSoLanTaiPham.getText().toString().equals(""))
				{
					preStatement.setInt(31, 0);
				}
				else 
				{
					
					preStatement.setInt(31, Integer.parseInt(txtSoLanTaiPham.getText()));
					
					
				}
				
				
				preStatement.setString(32, txtaGhiChuKhac.getText());
				
				
				
				
				int x=preStatement.executeUpdate();
				if(x>0)
				{
					hienThiDSVPHLATDB();
				}
				
				
				
			}
			
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}

	protected void xuLyXoa() {
		
		if(kiemTraMaTonTai(txtSTT.getText())==false)
		{
			ImageIcon icon=new ImageIcon("hinhanh/loixoa.png");
			JOptionPane.showMessageDialog(null, "Mã không tồn tại","Thông báo xóa",
					JOptionPane.OK_OPTION,icon);
			return;
			
		}
		else
		{
			ImageIcon icon=new ImageIcon("hinhanh/question.png");
			int ret=JOptionPane.showConfirmDialog(null, "Có chắc xóa mã"+txtSTT.getText()+"này không ?",
					"Xác nhận xóa",
					JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,icon);
			if(ret==JOptionPane.YES_OPTION)
			{
				try
				{
					String sql="delete from NHAPTHONGTIN where STT=?";
					preStatement=conn.prepareStatement(sql);
					preStatement.setString(1, txtSTT.getText());
					int x=preStatement.executeUpdate();
					if(x>0)
					{
						hienThiDSVPHLATDB();
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			
		}
		
	}

	//Region Hien Thi Chi Tiet Vi pHam Khi Bam Vao

	private void hienThiChiTietTHVP(String ma) {
		cboXa.removeAllItems();		// bo toan bo do khi chn huyen no cap nhap vao day
		fillCboXa();  // chen vao o day de khi chon thi xa duoc cap nhap moi
		try
		{
			String sql="select * from NHAPTHONGTIN where STT=?";
			preStatement=conn.prepareStatement(sql);
			preStatement.setString(1,ma);
			ResultSet rs=preStatement.executeQuery();
			if(rs.next())
			
			{
				txtSTT.setText(rs.getString("STT"));				
					
//				if(rs.getString("NgayPhatSinh")!=null)
//				{
//				txtNgayPhatSinh.setText(KiemTraDuLieuDauVao.KiemTraNgayThang(rs.getDate("NgayPhatSinh")));
//				}
//				else 
//				{
//					txtNgayPhatSinh.setText("");
//				}
//				
//				//	txtNgayPhatSinh.setText(sdf_ddMMyyyy.format(rs.getDate("NgayPhatSinh"))); dung ra viet nhu nay nhung vi su dung ham kiem tra
				
				txtNgayPhatSinh.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayPhatSinh"));
					
								
				// duyet qua Tuyen Duong									
				
			for(int j=0;j<cboTuyenDuong.getItemCount();j++)
			{
							
				if(cboTuyenDuong.getItemAt(j).getTenDuong().equals(rs.getString("TenDuong")))
				{
					
			cboTuyenDuong.setSelectedIndex(j);
				
				}
				
				else if (rs.getString("TenDuong")==""){
					cboTuyenDuong.setSelectedIndex(-1);
					}										
			}
			
			
			
			for(int k=0;k<cboHuyen.getItemCount();k++)
			{
				 if(rs.getString("Huyen")==""||rs.getString("Huyen")==null){
					cboHuyen.setSelectedIndex(-1);
					}						
			
				 else	if(cboHuyen.getItemAt(k).getTenHuyen().equals(rs.getString("Huyen")))
				{
					
					cboHuyen.setSelectedIndex(k);
				
				}
									
			}
			
			
			for(int l=0;l<cboXa.getItemCount();l++)
			{
				
				 if(rs.getString("Xa")==""||rs.getString("Xa")==null){
					cboXa.setSelectedIndex(-1);
					}						
				
				 else	if(cboXa.getItemAt(l).getTenXa().equals(rs.getString("Xa")))
				{
					
					cboXa.setSelectedIndex(l);
				
				}
										
			}
			
			txtNguoiXuLy.setText(rs.getString("NguoiXuLy"));	
			
			
			
			for(int m=0;m<cboDoiXuLy.getItemCount();m++)
			{
					
				 if (rs.getString("DoiXuLy")==null){
					cboDoiXuLy.setSelectedIndex(-1);
					}				
				
				 else	if(cboDoiXuLy.getItemAt(m).getTenDoi().equals(rs.getString("DoiXuLy")))
				{
					
					cboDoiXuLy.setSelectedIndex(m);
				
				}
									
			}
			
			txtLyTrinh.setText(rs.getString("LyTrinh"));
			txtaNguoiViPham.setText(rs.getString("NguoiViPham"));
			txtaNoiDungViPham.setText(rs.getString("NoiDungViPham"));
			
						
			for(int n=0;n<cboLoaiViPham.getItemCount();n++)
			{
					
				 if(rs.getString("LoaiViPham")==""||rs.getString("LoaiViPham")==null){
					cboLoaiViPham.setSelectedIndex(-1);
					}				
				 else if(cboLoaiViPham.getItemAt(n).getLoaiViPham().equals(rs.getString("LoaiViPham")))
				{
					
					cboLoaiViPham.setSelectedIndex(n);
				
				}
									
			}
			
			
			
			
			
		// Start CACH KHAC NEU SU DUNG KIEU KIEM TRA 
			
//			if(rs.getString("NgayLapBBKC")==null)		// loai bo truong hop co gia tri null o ngay thang
//			{
//				txtNgayLapBBKC.setText("");
//			}
//			else 
//			{
//				txtNgayLapBBKC.setText(KiemTraDuLieuDauVao.KiemTraNgayThang(rs.getDate("NgayLapBBKC")));
//			}
			
			// End CACH KHAC NEU SU DUNG KIEU KIEM TRA 
			
			txtNgayLapBBKC.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayLapBBKC"));
			
					
			txtNgayCGBBKC.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayChuyenGiaoBBKC"));
			
			txtNgayLapBBVPHC.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayLapBBVPHC"));
			
			txtSoBBVPHC.setText(rs.getString("SoBBVPHC"));
			
			txtNgayCGBBVPHC.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayChuyenGiaoBBVPHC"));
			
			txtSoBBCGVPHC.setText(rs.getString("SoBBChuyenGiao"));
			
			txtNgayQD.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayQuyetDinh"));
			txtSoQuyetDinh.setText(rs.getString("SoQuyetDinh"));
			txtSoTien.setText(Float.toString(rs.getFloat("SoTien")));
			
			txtNgayBBLV.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayBBLV"));			
			txtSoBBLV.setText(rs.getString("SoBBLV"));
			
			txtNgayBBCK.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayBBCamKet"));			
			txtSoBBCK.setText(rs.getString("SoBBCamKet"));
			
			txtaKetQuaXuLy.setText(rs.getString("KetQuaXuLy"));
			
			
			
			for(int o=0;o<cboGhiChu.getItemCount();o++)
			{
					
				 if(rs.getString("GhiChu")==null){
					cboGhiChu.setSelectedIndex(-1);
					}						
				 else if((rs.getString("GhiChu").equals(cboGhiChu.getItemAt(o).getGhiChu())))
				{
					
					cboGhiChu.setSelectedIndex(o);
				
				}
				
						
			}
			

			
			txtNgayThaoDo.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayThaoDo"));		
			
			txtNgayCuongChe.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayCuongChe"));	
			txtNgayTuyenTruyen.setText(KiemTraDuLieuDauVao.KiemTraNgayThang2(rs,"NgayTuyenTruyen"));		
			
			txtSoLanTaiPham.setText(Integer.toString(rs.getInt("SoLanTaiPham")));
			
			txtaGhiChuKhac.setText(rs.getString("GhiChuKhac"));
			
			
			for(int p=0;p<cboKhongThongKe.getItemCount();p++)
			{
				 if(rs.getString("KhongThongKe")==null){
					cboKhongThongKe.setSelectedIndex(-1);
					}				
							
				 else if((rs.getString("KhongThongKe").equals(cboKhongThongKe.getItemAt(p).getKhongThongKe())))
				{
					
					cboKhongThongKe.setSelectedIndex(p);
				
				}
				
							
			}
			
			
			
			}
					
						
		}

		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
	}
	
	

	public void showWinDow2()
	{
		this.setSize(1350, 700);							// nghien cuu xem theo kich thuoc cua man hinh, co thong bao
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	//EndRegion
	
	
	private void fillCboLoaiViPham() {
		Connection connection=KetNoi.getConnect("XPFINAL\\SQLEXPRESS", "HANHLANGATDBJAVA");
		
		try
		{
			String sql="select * from LOAIVP";
			PreparedStatement preStatement=connection.prepareStatement(sql);
			ResultSet result=preStatement.executeQuery();
			
			while (result.next()) 
			{
				LoaiViPham lvp=new LoaiViPham();
				
				lvp.setsTT(result.getInt("Stt"));
				lvp.setLoaiViPham(result.getString("LoaiViPham"));
								
				cboLoaiViPham.addItem(lvp);
				
			//	JOptionPane.showMessageDialog(null, "anh");
							
			}
		}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
	}		
	
	
	
	private void fillCboTuyenDuong() {
	
	for(TuyenDuong td:TuyenDuongService.LayDSTuyenDuong())		// dua truc tiep vi static va tra ve la dsLoaiviPham
	{
		cboTuyenDuong.addItem(td);		
	}
	
	}
	
	
	private void fillCboDoiXuLy() {
		
		for(DoiXuLy dxl:DoiXuLyService.LayDSDoiXuLy())		// dua truc tiep vi static va tra ve la dsLoaiviPham
		{
			cboDoiXuLy.addItem(dxl);		
		}
		
		}
	
private void fillCboGhiChu() {
		
		for(GhiChu gc:GhiChuService.LayDSGhiChu())		// dua truc tiep vi static va tra ve la dsLoaiviPham
		{
			cboGhiChu.addItem(gc);		
		}
		
		}
	
	
private void fillCboHuyen() {
	
	for(Huyen huyen:HuyenService.LayDSHuyen())		// dua truc tiep vi static va tra ve la dsLoaiviPham
	{
		cboHuyen.addItem(huyen);
		
	}
	
	}
	




private void fillCboXa() {
	
	for(Xa xa:XaService.LayDSXa())		// dua truc tiep vi static va tra ve la dsLoaiviPham
	{
		
		
		cboXa.addItem(xa);		
	
		
	}
	
	}


private void fillCboKhongThongKe() {
	
	for(KhongThongKe ktk:KhongThongKeService.LayDSKhongThongKe())		// dua truc tiep vi static va tra ve la dsLoaiviPham
	{
		
		
		cboKhongThongKe.addItem(ktk);		
	
		
	}
	
	}


private void hienThiDSVPHLATDBTheoDuongHuyenXa(String DuongHuyenXa, String TenDuongHuyenXa) {
	CallableStatement callStatement = null;
	try
	{
		switch(DuongHuyenXa)
		{
		case "Duong":
			callStatement=conn.prepareCall("{call LayDSVPTheoTuyenDuong(?)}");
			callStatement.setString(1,TenDuongHuyenXa);
			break;
		case "Huyen":
			callStatement=conn.prepareCall("{call LayDSVPTheoDiaBanHuyen(?)}");
			callStatement.setString(1,TenDuongHuyenXa);
			break;
		case "Xa":
			callStatement=conn.prepareCall("{call LayDSVPTheoDiaBanXa(?)}");
			callStatement.setString(1,TenDuongHuyenXa);
			break;		
			
		}
		
		
		result=callStatement.executeQuery();
		dtm.setRowCount(0);
		while(result.next())
		{
			Vector<Object>vec =new Vector<Object>();
			vec.add(result.getInt("STT"));
			vec.add(result.getDate("NgayPhatSinh"));
			vec.add(result.getString("Huyen"));
			vec.add(result.getString("Xa"));
			vec.add(result.getString("NguoiXuLy"));
			vec.add(result.getString("DoiXuLy"));
			vec.add(result.getString("TenDuong"));
			vec.add(result.getString("LyTrinh"));
			vec.add(result.getString("NguoiViPham"));
			vec.add(result.getString("NoiDungViPham"));
			vec.add(result.getString("LoaiViPham"));
			
			vec.add(result.getDate("NgayLapBBKC"));
			vec.add(result.getDate("NgayChuyenGiaoBBKC"));
			vec.add(result.getDate("NgayLapBBVPHC"));
			vec.add(result.getString("SoBBVPHC"));
			
			vec.add(result.getDate("NgayChuyenGiaoBBVPHC"));
			vec.add(result.getString("SoBBChuyenGiao"));
			vec.add(result.getDate("NgayQuyetDinh"));
			vec.add(result.getString("SoQuyetDinh"));
			vec.add(result.getFloat("SoTien"));
			
			vec.add(result.getDate("NgayBBLV"));
			vec.add(result.getString("SoBBLV"));
			vec.add(result.getDate("NgayBBCamKet"));
			vec.add(result.getString("SoBBCamKet"));
			
			vec.add(result.getString("KetQuaXuLy"));
			vec.add(result.getString("GhiChu"));
			vec.add(result.getString("KhongThongKe"));
			
			vec.add(result.getDate("NgayThaoDo"));
			vec.add(result.getDate("NgayCuongChe"));
			vec.add(result.getDate("NgayTuyenTruyen"));
			vec.add(result.getInt("SoLanTaiPham"));
			vec.add(result.getString("GhiChuKhac"));
			
			dtm.addRow(vec);
			
			ThongTinTongQuat(dtm);
		
		}
				
	}
	
	catch(Exception ex)
	
	{
		ex.printStackTrace();
	}
}



	


private void hienThiDSVPHLATDBTinhLo() 
{
	
	try{
		CallableStatement 	callStatement=conn.prepareCall("{call LayDSVPTheoTuyenDuongTinhLo}");
//			callStatement.setString(1,Duong1);	
//			callStatement.setString(2,Duong2);	
//			callStatement.setString(3,Duong3);	
//			callStatement.setString(4,Duong4);	
//			callStatement.setString(5,Duong5);	
//			callStatement.setString(6,Duong6);	
//			callStatement.setString(7,Duong7);	
//			callStatement.setString(8,Duong8);	
			
//			callStatement.setString(9,Duong9);	
//			callStatement.setString(10,Duong10);	
//			callStatement.setString(11,Duong11);	
		
			
		result=callStatement.executeQuery();
		dtm.setRowCount(0);
		while(result.next())
		{
			Vector<Object>vec =new Vector<Object>();
			vec.add(result.getInt("STT"));
			vec.add(result.getDate("NgayPhatSinh"));
			vec.add(result.getString("Huyen"));
			vec.add(result.getString("Xa"));
			vec.add(result.getString("NguoiXuLy"));
			vec.add(result.getString("DoiXuLy"));
			vec.add(result.getString("TenDuong"));
			vec.add(result.getString("LyTrinh"));
			vec.add(result.getString("NguoiViPham"));
			vec.add(result.getString("NoiDungViPham"));
			vec.add(result.getString("LoaiViPham"));
			
			vec.add(result.getDate("NgayLapBBKC"));
			vec.add(result.getDate("NgayChuyenGiaoBBKC"));
			vec.add(result.getDate("NgayLapBBVPHC"));
			vec.add(result.getString("SoBBVPHC"));
			
			vec.add(result.getDate("NgayChuyenGiaoBBVPHC"));
			vec.add(result.getString("SoBBChuyenGiao"));
			vec.add(result.getDate("NgayQuyetDinh"));
			vec.add(result.getString("SoQuyetDinh"));
			vec.add(result.getFloat("SoTien"));
			
			vec.add(result.getDate("NgayBBLV"));
			vec.add(result.getString("SoBBLV"));
			vec.add(result.getDate("NgayBBCamKet"));
			vec.add(result.getString("SoBBCamKet"));
			
			vec.add(result.getString("KetQuaXuLy"));
			vec.add(result.getString("GhiChu"));
			vec.add(result.getString("KhongThongKe"));
			
			vec.add(result.getDate("NgayThaoDo"));
			vec.add(result.getDate("NgayCuongChe"));
			vec.add(result.getDate("NgayTuyenTruyen"));
			vec.add(result.getInt("SoLanTaiPham"));
			vec.add(result.getString("GhiChuKhac"));
			
			dtm.addRow(vec);
			
			ThongTinTongQuat(dtm);
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		
}

private void hienThiDSVPHLATDBQuocLo(String Duong1, String Duong2,String Duong3,String Duong4) 
{
	
	try{
		CallableStatement 	callStatement=conn.prepareCall("{call LayDSVPTheoTuyenDuongQuocLo(?,?,?,?)}");
			callStatement.setString(1,Duong1);	
			callStatement.setString(2,Duong2);	
			callStatement.setString(3,Duong3);	
			callStatement.setString(4,Duong4);	
				
			
		result=callStatement.executeQuery();
		dtm.setRowCount(0);
		while(result.next())
		{
			Vector<Object>vec =new Vector<Object>();
			vec.add(result.getInt("STT"));
			vec.add(result.getDate("NgayPhatSinh"));
			vec.add(result.getString("Huyen"));
			vec.add(result.getString("Xa"));
			vec.add(result.getString("NguoiXuLy"));
			vec.add(result.getString("DoiXuLy"));
			vec.add(result.getString("TenDuong"));
			vec.add(result.getString("LyTrinh"));
			vec.add(result.getString("NguoiViPham"));
			vec.add(result.getString("NoiDungViPham"));
			vec.add(result.getString("LoaiViPham"));
			
			vec.add(result.getDate("NgayLapBBKC"));
			vec.add(result.getDate("NgayChuyenGiaoBBKC"));
			vec.add(result.getDate("NgayLapBBVPHC"));
			vec.add(result.getString("SoBBVPHC"));
			
			vec.add(result.getDate("NgayChuyenGiaoBBVPHC"));
			vec.add(result.getString("SoBBChuyenGiao"));
			vec.add(result.getDate("NgayQuyetDinh"));
			vec.add(result.getString("SoQuyetDinh"));
			vec.add(result.getFloat("SoTien"));
			
			vec.add(result.getDate("NgayBBLV"));
			vec.add(result.getString("SoBBLV"));
			vec.add(result.getDate("NgayBBCamKet"));
			vec.add(result.getString("SoBBCamKet"));
			
			vec.add(result.getString("KetQuaXuLy"));
			vec.add(result.getString("GhiChu"));
			vec.add(result.getString("KhongThongKe"));
			
			vec.add(result.getDate("NgayThaoDo"));
			vec.add(result.getDate("NgayCuongChe"));
			vec.add(result.getDate("NgayTuyenTruyen"));
			vec.add(result.getInt("SoLanTaiPham"));
			vec.add(result.getString("GhiChuKhac"));
			
			dtm.addRow(vec);
			
			ThongTinTongQuat(dtm);
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		
}

Date date = new Date();
private void ThongTinTongQuat(DefaultTableModel dtm)
{
	
	if(dtm.getRowCount()>0){
    lblThongTinTongQuat.setText("Ngày hiện tại là: " +sdf_ddMMyyyy.format(date).toString()
  		  +"; Tổng số có "+Integer.toString( dtm.getRowCount())+" trường hợp vi phạm");
    
	}
	
	else  lblThongTinTongQuat.setText("Ngày hiện tại là: " +sdf_ddMMyyyy.format(date).toString()
	  		  +"; Tổng số có  0 trường hợp vi phạm");
}


private void LayDSxaTheoHuyen() { 
	// private void comboboxAPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)
	String id_Huyen="TP";
	
	 if(cboHuyen.getSelectedItem()!=null)
	{
	int luu=cboXa.getSelectedIndex();
	Huyen huyen =(Huyen) cboHuyen.getSelectedItem();			
     id_Huyen = huyen.getIdHuyen();
	
    
 //   System.out.println(id_Huyen);
    
    String sql = "select * from XA where ID_HUYEN =?";
  
    cboXa.removeAllItems(); // <- Clear comboboxB

    try {
        preStatement=conn.prepareStatement(sql);
		preStatement.setString(1,id_Huyen);
		ResultSet rs=preStatement.executeQuery();
		
        
        while (rs.next()) {  // <- Include all authors found
          
                                 
           	Xa xa=new Xa();			
			xa.setIdXa(rs.getInt("ID_XA"));
			xa.setTenXa(rs.getString("TEN_XA"));
			xa.setIdHuyen(rs.getString("ID_HUYEN"));
           
           cboXa.addItem(xa);
           
        }
        
    //    System.out.println("da chen xong");
        cboXa.setSelectedIndex(luu);

   } catch(Exception e) {
       JOptionPane.showMessageDialog(null,e);
   }
    
	}
}





}

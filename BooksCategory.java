import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Arrays;

public class BooksCategory {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BooksCategory::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE - Books");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        frame.add(createNavBar(frame), BorderLayout.NORTH);
        frame.add(createContentPanel(frame), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Navigation bar, highlights BOOKS
    private static JPanel createNavBar(final JFrame frame) {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(new Color(180, 140, 200));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 10));

        JLabel logo = new JLabel("📖 BOOKVERSE");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("SansSerif", Font.BOLD, 20));
        logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                MainPage.main(new String[]{});
            }
        });
        nav.add(logo, BorderLayout.WEST);

        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 5));
        menuPanel.setOpaque(false);
        String[] items = {"HOME", "BOOKS", "NEW RELEASE", "CONTACT US"};
        for (String item : items) {
            JButton btn = new JButton(item);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(180, 140, 200));
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            if ("BOOKS".equals(item)) {
                btn.setForeground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(255, 255, 255, 200)),
                        BorderFactory.createEmptyBorder(4, 6, 2, 6)
                ));
            }
            
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setForeground(new Color(255, 200, 255));
                    btn.setFont(new Font("SansSerif", Font.BOLD, 15));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setForeground(Color.WHITE);
                    btn.setFont(new Font("SansSerif", Font.BOLD, 14));
                }
            });
            
            btn.addActionListener(e -> {
                switch (item) {
                    case "HOME":
                        frame.dispose();
                        MainPage.main(new String[]{});
                        break;
                    case "BOOKS":
                        frame.dispose();
                        BooksCategory.main(new String[]{});
                        break;
                    case "NEW RELEASE":
                        JOptionPane.showMessageDialog(frame, "New Release section coming soon!");
                        break;
                    case "CONTACT US":
                        JOptionPane.showMessageDialog(frame, "Contact: info@bookverse.com\nPhone: +213 123 456 789");
                        break;
                }
            });
            menuPanel.add(btn);
        }
        nav.add(menuPanel, BorderLayout.CENTER);

        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        iconPanel.setOpaque(false);
        java.util.function.Function<String, ImageIcon> resizeIcon = p -> scaleIconSafe(loadIcon(p), 24, 24);
        for (String icon : new String[]{"facebook.png", "instagram.png", "linkedin.png", "search.png"}) {
            JLabel l = new JLabel(resizeIcon.apply(icon));
            l.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            l.addMouseListener(new HoverScaleAdapter(l, 24, 28));
            iconPanel.add(l);
        }
        nav.add(iconPanel, BorderLayout.EAST);

        return nav;
    }

    // Main content: grid of product cards wrapped in a centered container + scroll
    private static JScrollPane createContentPanel(final JFrame frame) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(20, 20, 20, 20);
        c.gridx = 0; c.gridy = 0;

        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setOpaque(false);

        List<Book> sample = Arrays.asList(
            new Book("Calculus Made Clear", "A. Mathematician", "1200 DA", "book.png"),
            new Book("Intro to Algorithms", "C. Programmer", "2000 DA", "books.png"),
            new Book("Physics Essentials", "D. Scientist", "1500 DA", "book.png"),
            new Book("Islamic Thought", "E. Scholar", "950 DA", "books.png"),
            new Book("Data Structures", "F. Engineer", "1800 DA", "book.png"),
            new Book("Modern Chemistry", "G. Researcher", "1400 DA", "books.png")
        );

        for (Book b : sample) grid.add(createBookCard(b, frame));

        container.add(grid, c);

        JScrollPane sp = new JScrollPane(container);
        sp.setBorder(null);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return sp;
    }

    // Create a single book card component with click navigation
    private static JPanel createBookCard(Book book, final JFrame frame) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(250, 250, 252));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230,230,230)),
            BorderFactory.createEmptyBorder(12,12,12,12)
        ));
        card.setPreferredSize(new Dimension(300, 320));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // image
        ImageIcon raw = loadIcon(book.imagePath);
        ImageIcon imgIcon = scaleIconSafe(raw, 140, 190);
        JLabel imgLabel = new JLabel(imgIcon);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgLabel.addMouseListener(new HoverScaleAdapter(imgLabel, 140, 150));
        card.add(imgLabel);
        card.add(Box.createVerticalStrut(10));

        // title
        JLabel title = new JLabel("<html><b>" + book.title + "</b></html>");
        title.setFont(new Font("Serif", Font.BOLD, 16));
        title.setForeground(new Color(60, 0, 90));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(title);

        // author
        JLabel author = new JLabel(book.author);
        author.setFont(new Font("SansSerif", Font.PLAIN, 13));
        author.setForeground(new Color(80, 80, 90));
        author.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(author);

        card.add(Box.createVerticalGlue());

        // bottom row price + button
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        bottom.setOpaque(false);

        JLabel price = new JLabel(book.price);
        price.setFont(new Font("SansSerif", Font.BOLD, 14));
        price.setForeground(new Color(120, 0, 180));
        bottom.add(price);

        JButton buy = new JButton("Buy Now");
        buy.setBackground(new Color(255, 64, 129));
        buy.setForeground(Color.WHITE);
        buy.setFocusPainted(false);
        buy.setBorder(BorderFactory.createEmptyBorder(6,12,6,12));
        buy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buy.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                buy.setBackground(new Color(220,20,100));
            }
            @Override public void mouseExited(MouseEvent e) {
                buy.setBackground(new Color(255,64,129));
            }
        });
        // click on Buy Now button -> open productDisplay page
        buy.addActionListener(e -> {
            frame.dispose();
            ProductDisplay.main(new String[]{});
        });
        bottom.add(buy);

        card.add(bottom);

        // click on entire card -> open productDisplay page
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                ProductDisplay.main(new String[]{});
            }
        });

        return card;
    }

    // small helper to load from classpath or filesystem and fallback to 1x1 transparent
    private static ImageIcon loadIcon(String path) {
        java.net.URL url = BooksCategory.class.getResource(path.startsWith("/") ? path : "/" + path);
        if (url != null) return new ImageIcon(url);
        java.io.File f = new java.io.File(path);
        if (f.exists()) return new ImageIcon(path);
        BufferedImage buf = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        return new ImageIcon(buf);
    }

    // safe scale returning an ImageIcon with positive size
    private static ImageIcon scaleIconSafe(ImageIcon icon, int w, int h) {
        if (icon == null) icon = new ImageIcon(new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB));
        int iw = icon.getIconWidth(), ih = icon.getIconHeight();
        if (iw <= 0 || ih <= 0) {
            BufferedImage buf = new BufferedImage(Math.max(1,w), Math.max(1,h), BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(buf);
        }
        Image img = icon.getImage().getScaledInstance(Math.max(1,w), Math.max(1,h), Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // MouseAdapter to scale an image label on hover
    private static class HoverScaleAdapter extends MouseAdapter {
        private final JLabel label;
        private final int normalW, hoverW;
        public HoverScaleAdapter(JLabel label, int normalW, int hoverW) {
            this.label = label; this.normalW = normalW; this.hoverW = hoverW;
        }
        public void mouseEntered(MouseEvent e) {
            Icon ic = label.getIcon();
            if (ic instanceof ImageIcon) {
                Image img = ((ImageIcon) ic).getImage();
                Image scaled = img.getScaledInstance(hoverW, (int)(hoverW*1.36), Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            }
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        public void mouseExited(MouseEvent e) {
            Icon ic = label.getIcon();
            if (ic instanceof ImageIcon) {
                Image img = ((ImageIcon) ic).getImage();
                Image scaled = img.getScaledInstance(normalW, (int)(normalW*1.36), Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            }
            label.setCursor(Cursor.getDefaultCursor());
        }
    }

    // simple data holder
    private static class Book {
        String title, author, price, imagePath;
        Book(String t, String a, String p, String ip) { title = t; author = a; price = p; imagePath = ip; }
    }
}
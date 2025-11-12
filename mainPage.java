import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class MainPage {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainPage::createAndShow);
    }

    private static void createAndShow() {
        final JFrame frame = new JFrame("BOOKVERSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(new Color(180, 140, 200)); // soft purple
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

        java.util.function.Function<String, ImageIcon> resizeIcon = (path) -> scaleIconSafe(loadIcon(path), 24, 24);

        JLabel facebook = new JLabel(resizeIcon.apply("facebook.png"));
        JLabel insta    = new JLabel(resizeIcon.apply("instagram.png"));
        JLabel linkedin = new JLabel(resizeIcon.apply("linkedin.png"));
        JLabel search   = new JLabel(resizeIcon.apply("search.png"));

        for (JLabel ic : new JLabel[]{facebook, insta, linkedin, search}) {
            ic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            ic.addMouseListener(new HoverScaleAdapter(ic, 24, 28));
            iconPanel.add(ic);
        }
        nav.add(iconPanel, BorderLayout.EAST);

        frame.add(nav, BorderLayout.NORTH);

        JPanel hero = new JPanel(new GridBagLayout());
        hero.setBackground(new Color(250, 250, 252));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 40, 0, 40);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 1;

        JPanel left = new JPanel();
        left.setOpaque(false);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        titlePanel.setOpaque(false);

        java.util.function.Function<String, ImageIcon> resizeSmallIcon = (path) -> scaleIconSafe(loadIcon(path), 20, 20);

        JLabel icon1 = new JLabel(resizeSmallIcon.apply("star.png"));
        JLabel icon2 = new JLabel(resizeSmallIcon.apply("openbook.png"));

        JLabel welcomeTitle = new JLabel("Welcome To BOOKVERSE");
        welcomeTitle.setFont(new Font("Serif", Font.BOLD, 42));
        welcomeTitle.setForeground(new Color(60, 0, 90));

        titlePanel.add(icon1);
        titlePanel.add(icon2);
        titlePanel.add(welcomeTitle);

        JLabel welcomeText = new JLabel("<html>"
                + "BOOKVERSE offers a curated selection of essential books<br>"
                + "in three key domains: Islamic knowledge, scientific discovery,<br>"
                + "and computer science innovation.<br>"
                + "It's a destination for readers seeking spiritual insight,<br>"
                + "academic growth, and technical expertise — all in one place."
                + "</html>");
        welcomeText.setFont(new Font("SansSerif", Font.PLAIN, 18));
        welcomeText.setForeground(new Color(45, 45, 55));
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeText.setHorizontalAlignment(SwingConstants.CENTER);

        java.util.function.Function<String, ImageIcon> resizeButtonIcon = (path) -> scaleIconSafe(loadIcon(path), 18, 18);

        JButton readMore = new JButton("READ MORE ", resizeButtonIcon.apply("arrow.png"));
        readMore.setFont(new Font("SansSerif", Font.BOLD, 15));
        readMore.setForeground(new Color(120, 0, 180));
        readMore.setOpaque(false);
        readMore.setContentAreaFilled(false);
        readMore.setBorderPainted(false);
        readMore.setFocusPainted(false);
        readMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        readMore.setAlignmentX(Component.CENTER_ALIGNMENT);
        readMore.setHorizontalTextPosition(SwingConstants.LEFT);
        readMore.setIconTextGap(8);
        readMore.addActionListener(e -> {
            frame.dispose();
            BooksCategory.main(new String[]{});
        });

        left.add(Box.createVerticalStrut(10));
        left.add(titlePanel);
        left.add(Box.createVerticalStrut(14));
        left.add(welcomeText);
        left.add(Box.createVerticalStrut(24));
        left.add(readMore);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        hero.add(left, gbc);

        JPanel right = new JPanel();
        right.setOpaque(false);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ImageIcon bookCover = loadIcon("book.png");
        Image baseImg = bookCover.getImage();

        int bw = Math.max(1, bookCover.getIconWidth());
        int bh = Math.max(1, bookCover.getIconHeight());

        Image scaledImg = baseImg.getScaledInstance(Math.max(1, (int)(bw * 0.7)), Math.max(1, (int)(bh * 0.7)), Image.SCALE_SMOOTH);
        ImageIcon smallBookCover = new ImageIcon(scaledImg);

        JLabel bookLabel = new JLabel(smallBookCover);
        bookLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bookLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bookLabel.addMouseListener(new HoverScaleAdapter(bookLabel, (int)(bw*0.7), (int)(bw*0.75)));
        // click on book image -> open productPage
        bookLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                BooksCategory.main(new String[]{});
            }
        });
        
        right.add(Box.createVerticalGlue());
        right.add(bookLabel);
        right.add(Box.createVerticalGlue());

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        hero.add(right, gbc);

        frame.add(hero, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static ImageIcon loadIcon(String path) {
        java.net.URL url = MainPage.class.getResource(path.startsWith("/") ? path : "/" + path);
        if (url != null) return new ImageIcon(url);
        java.io.File f = new java.io.File(path);
        if (f.exists()) return new ImageIcon(path);
        BufferedImage buf = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        System.err.println("Image not found: " + path + " (working dir: " + System.getProperty("user.dir") + ")");
        return new ImageIcon(buf);
    }

    private static ImageIcon scaleIconSafe(ImageIcon icon, int w, int h) {
        if (icon == null) icon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));
        int iw = icon.getIconWidth(), ih = icon.getIconHeight();
        if (iw <= 0 || ih <= 0) {
            BufferedImage buf = new BufferedImage(Math.max(1, w), Math.max(1, h), BufferedImage.TYPE_INT_ARGB);
            return new ImageIcon(buf);
        }
        Image img = icon.getImage().getScaledInstance(Math.max(1, w), Math.max(1, h), Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private static class HoverScaleAdapter extends MouseAdapter {
        private final JLabel label;
        private final int normalW, hoverW;

        HoverScaleAdapter(JLabel label, int normalW, int hoverW) {
            this.label = label;
            this.normalW = normalW;
            this.hoverW = hoverW;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Icon ic = label.getIcon();
            if (ic instanceof ImageIcon) {
                Image img = ((ImageIcon) ic).getImage();
                int h = (int) (hoverW * ((double) img.getHeight(null) / Math.max(1, img.getWidth(null))));
                Image scaled = img.getScaledInstance(hoverW, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            }
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Icon ic = label.getIcon();
            if (ic instanceof ImageIcon) {
                Image img = ((ImageIcon) ic).getImage();
                int h = (int) (normalW * ((double) img.getHeight(null) / Math.max(1, img.getWidth(null))));
                Image scaled = img.getScaledInstance(normalW, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
            }
            label.setCursor(Cursor.getDefaultCursor());
        }
    }
}
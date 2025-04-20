package GUI_DATPHONG;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import GUI_DANGNHAP_DANGKY.CustomMessage;

public class CalendarPopup {
    private final JDialog calendarDialog;
    private final EventHandler eventHandler;
    private final CustomMessage customMessage;
    private final FindRoom parent;
    private final Component relativeTo;

    public CalendarPopup(FindRoom parent, Component relativeTo, boolean isCheckIn, EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.customMessage = new CustomMessage();
        this.parent = parent;
        this.relativeTo = relativeTo;

        calendarDialog = new JDialog();
        calendarDialog.setUndecorated(true);
        calendarDialog.setLayout(new BorderLayout());
        calendarDialog.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 255), 2));

        UIManager.put("ScrollBar.thumb", new Color(160, 180, 255));
        UIManager.put("ScrollBar.thumbDarkShadow", new Color(100, 130, 220));
        UIManager.put("ScrollBar.thumbHighlight", new Color(190, 210, 255));
        UIManager.put("ScrollBar.track", Color.WHITE);
        UIManager.put("ScrollBar.width", 8);

        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);

        String[] months = { "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5", "Tháng 6",
                "Tháng 7", "Tháng 8", "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12" };
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
        monthCombo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        monthCombo.setBackground(new Color(245, 245, 255));

        SpinnerNumberModel yearModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 2000, 2050, 1);
        JSpinner yearSpinner = new JSpinner(yearModel);
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "####");
        yearSpinner.setEditor(yearEditor);

        JPanel monthYearPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        monthYearPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        monthYearPanel.setBackground(Color.WHITE);
        monthYearPanel.add(monthCombo);
        monthYearPanel.add(yearSpinner);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(monthYearPanel, BorderLayout.CENTER);

        JPanel daysHeaderPanel = new JPanel(new GridLayout(1, 7));
        daysHeaderPanel.setBackground(new Color(83, 185, 230));
        String[] dayNames = { "Th 2", "Th 3", "Th 4", "Th 5", "Th 6", "Th 7", "CN" };
        for (String day : dayNames) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setForeground(Color.WHITE);
            dayLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            dayLabel.setOpaque(true);
            dayLabel.setBackground(new Color(83, 185, 230));
            dayLabel.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));
            daysHeaderPanel.add(dayLabel);
        }

        JPanel daysPanel = new JPanel(new GridLayout(6, 7));
        daysPanel.setBackground(Color.WHITE);

        updateCalendarDays(daysPanel, monthCombo.getSelectedIndex(), (Integer) yearSpinner.getValue(), isCheckIn);

        ActionListener dateChangeListener = e -> updateCalendarDays(daysPanel,
                monthCombo.getSelectedIndex(),
                (Integer) yearSpinner.getValue(),
                isCheckIn);
        monthCombo.addActionListener(dateChangeListener);
        yearSpinner.addChangeListener(e -> dateChangeListener.actionPerformed(null));

        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(daysHeaderPanel, BorderLayout.CENTER);
        calendarPanel.add(daysPanel, BorderLayout.SOUTH);

        calendarDialog.add(calendarPanel);
        calendarDialog.pack();

        Point location = relativeTo.getLocationOnScreen();
        calendarDialog.setLocation(location.x, location.y + relativeTo.getHeight() + 5);
        calendarDialog.setVisible(true);
    }

    private void updateCalendarDays(JPanel daysPanel, int month, int year, boolean isCheckIn) {
        daysPanel.removeAll();

        Calendar now = Calendar.getInstance();
        int currentDay = now.get(Calendar.DAY_OF_MONTH);
        int currentMonth = now.get(Calendar.MONTH);
        int currentYear = now.get(Calendar.YEAR);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        firstDayOfWeek = firstDayOfWeek == 1 ? 7 : firstDayOfWeek - 1;

        for (int i = 1; i < firstDayOfWeek; i++) {
            daysPanel.add(createEmptyCell());
        }

        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= maxDays; i++) {
            final int dayNum = i;
            JPanel dayCell = new JPanel(new BorderLayout());
            dayCell.setBorder(BorderFactory.createEmptyBorder());
            dayCell.setBackground(new Color(245, 245, 255));
            dayCell.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dayCell.setOpaque(true);

            JLabel dayLabel = new JLabel(String.valueOf(i), SwingConstants.CENTER);
            dayLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            dayLabel.setForeground(Color.DARK_GRAY);

            if (i == currentDay && month == currentMonth && year == currentYear) {
                dayCell.setBackground(new Color(100, 235, 237));
                dayLabel.setForeground(Color.WHITE);
                dayLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            }

            dayCell.add(dayLabel, BorderLayout.CENTER);

            dayCell.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    dayCell.setBackground(new Color(159, 223, 237));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (dayNum == currentDay && month == currentMonth && year == currentYear) {
                        dayCell.setBackground(new Color(100, 235, 237));
                    } else {
                        dayCell.setBackground(new Color(245, 245, 255));
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    Calendar selected = Calendar.getInstance();
                    selected.set(Calendar.YEAR, year);
                    selected.set(Calendar.MONTH, month);
                    selected.set(Calendar.DAY_OF_MONTH, dayNum);

                    Date selectedDate = selected.getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    if (isCheckIn) {
                        // Check if the selected check-in date is before today
                        Calendar today = Calendar.getInstance();
                        today.set(Calendar.HOUR_OF_DAY, 0);
                        today.set(Calendar.MINUTE, 0);
                        today.set(Calendar.SECOND, 0);
                        today.set(Calendar.MILLISECOND, 0);
                        Calendar selectedCal = Calendar.getInstance();
                        selectedCal.setTime(selectedDate);
                        selectedCal.set(Calendar.HOUR_OF_DAY, 0);
                        selectedCal.set(Calendar.MINUTE, 0);
                        selectedCal.set(Calendar.SECOND, 0);
                        selectedCal.set(Calendar.MILLISECOND, 0);

                        if (selectedCal.before(today)) {
                            customMessage.showErrorMessage("Lỗi chọn ngày",
                                    "Ngày nhận phòng không được là ngày trong quá khứ.");
                            return;
                        }

                        eventHandler.setCheckInDate(selectedDate);
                        eventHandler.getCheckInDateLabel().setText(sdf.format(selectedDate));
                        calendarDialog.dispose();

                        // Automatically open check-out calendar
                        new CalendarPopup(parent, eventHandler.getCheckOutDateLabel(), false, eventHandler);
                    } else {
                        if (eventHandler.getCheckInDate() != null && selectedDate.before(eventHandler.getCheckInDate())) {
                            customMessage.showErrorMessage("Lỗi chọn ngày",
                                    "Ngày trả phòng phải sau ngày nhận phòng.");
                            return;
                        }
                        eventHandler.setCheckOutDate(selectedDate);
                        eventHandler.getCheckOutDateLabel().setText(sdf.format(selectedDate));
                        calendarDialog.dispose();
                    }
                }
            });

            daysPanel.add(dayCell);
        }

        int remaining = 42 - (firstDayOfWeek - 1) - maxDays;
        for (int i = 0; i < remaining; i++) {
            daysPanel.add(createEmptyCell());
        }

        daysPanel.revalidate();
        daysPanel.repaint();
    }

    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        return panel;
    }
}
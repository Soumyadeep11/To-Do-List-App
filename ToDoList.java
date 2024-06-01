package projects;

import java.awt.BorderLayout;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ToDoList extends JFrame {
	
    private List<Task> tasks;	
	private DefaultListModel<Task>listModel;
	private JList<Task> itemList;
	private JTextField itemField;
	private JComboBox<Task.Priority> priorityComboBox;
	
	public ToDoList() {
		setTitle("To Do List Application");
		setSize(500,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		tasks = new ArrayList<>();
		listModel = new DefaultListModel<>();
		itemList = new JList<>(listModel);
		itemField = new JTextField(20);
		priorityComboBox = new JComboBox<>(Task.Priority.values());		
		
	JScrollPane scrollPane = new JScrollPane(itemList);
	JButton addButton = new JButton("Add");
	JButton removeButton = new JButton("Remove");
	JButton markDoneButton = new JButton("Mark Done");
	JButton sortButton = new JButton("Sort");
	
	addButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String newItem = itemField.getText().trim();
			if(!newItem.isEmpty()) {
				Task.Priority priority = (Task.Priority)priorityComboBox.getSelectedItem();
				Task task = new Task(newItem, false, priority);
				tasks.add(task);
				listModel.addElement(task);
				itemField.setText("");
			}
		}
	});
	
	removeButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = itemList.getSelectedIndex();
			
			if(selectedIndex != -1) {
				tasks.remove(selectedIndex);
				listModel.remove(selectedIndex);
			}
		}
	});
	
	markDoneButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = itemList.getSelectedIndex();
			if(selectedIndex != -1) {
				Task task = itemList.getSelectedValue();
				task.setDone(true);
				itemList.repaint();
				
			}
		}
	});
	
	sortButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			sortTasks();
		}
	});
	
	JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
	inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
	inputPanel.add(new JLabel("Tasks:"));
	inputPanel.add(itemField);
	inputPanel.add(new JLabel("Priority:"));
	inputPanel.add(priorityComboBox);
	
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	buttonPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
	buttonPanel.add(addButton);
	buttonPanel.add(removeButton);
	buttonPanel.add(markDoneButton);
	buttonPanel.add(sortButton);
	
  JPanel controlPanel = new JPanel(new BorderLayout());
  controlPanel.add(inputPanel, BorderLayout.NORTH);
  controlPanel.add(buttonPanel, BorderLayout.CENTER);
  
  setLayout(new BorderLayout());
  add(controlPanel, BorderLayout.NORTH);
  add(scrollPane, BorderLayout.CENTER);
	}
  private void sortTasks() {
	
	  Collections.sort(tasks);
      listModel.clear();
      for(Task task : tasks) {
    	  listModel.addElement(task);
      }
  }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(() -> {
			ToDoList app = new ToDoList();
			app.setVisible(true);
		});
	}
private static class Task implements Comparable<Task>{
	private String name;
	private boolean done;
	private Priority priority;
	
	public enum Priority{
		LOW, NORMAL, HIGH
	}	
	
	public Task(String name , boolean done, Priority priority) {
		this.name=name;
		this.done=done;
		this.priority=priority;
		
	}
	public String getName() {
		return name;
	}
	
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done=done;
		
	}
	public Priority getPriority() {
		return priority;
	}
	
	public String toString() {
		return name + "(Priority: "+priority + ", Done: " + done + ")";
		
	}
	public int compareTo(Task other) {
		return this.priority.compareTo(other.priority);
	}
}
}

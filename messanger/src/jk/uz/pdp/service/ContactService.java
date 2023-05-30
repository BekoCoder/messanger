package jk.uz.pdp.service;

import jk.uz.pdp.model.Contact;
import jk.uz.pdp.model.ContactListType;
import jk.uz.pdp.model.User;

public class ContactService {
    private static Contact[] contactList = new Contact[100];
    private int index = 0;

    public boolean addContact(User currentUser, User contactUser) {
        boolean isTrue = checkContact(currentUser, contactUser);
        if (isTrue) {
            return false;
        }
        contactList[index++] = new Contact(currentUser.getId(), contactUser.getId());
        return true;
    }

    public User[] getContactList(User currentUser, ContactListType contactListType) {
        int cnt = 0;
        for (Contact contact : contactList) {
            if (contact != null) {
                if (contactListType.equals(ContactListType.CONTACT_LIST)) {
                    if (contact.getUserId().equals(currentUser.getId())) {
                        cnt++;
                    }
                } else if (contactListType.equals(ContactListType.SUBSCRIBE_LIST)) {
                    if (contact.getContactId().equals(currentUser.getId())) {
                        cnt++;
                    }
                }
            }
        }
        return this.fillUserFromContactList(new User[cnt], currentUser, contactListType);
    }

    private User[] fillUserFromContactList(User[] users, User currentUser, ContactListType contactListType) {
        int index = 0;
        for (Contact contact : contactList) {
            if (contact != null) {
                if (contactListType.equals(ContactListType.CONTACT_LIST)) {
                    if (contact.getUserId().equals(currentUser.getId())) {
                        users[index ++] = UserService.getUser(contact.getContactId());
                    }
                } else if (contactListType.equals(ContactListType.SUBSCRIBE_LIST)) {
                    if (contact.getContactId().equals(currentUser.getId())) {
                        users[index ++] = UserService.getUser(contact.getUserId());
                    }
                }
            }
        }
        return users;
    }

    private boolean checkContact(User currentUser, User contactUser) {
        for (Contact contact : contactList) {
            if (contact != null) {
                if (
                        contact.getUserId().equals(currentUser.getId())
                        && contact.getContactId().equals(contactUser.getId())
                ) {
                    return true;
                }
            }
        }
        return false;
    }

}

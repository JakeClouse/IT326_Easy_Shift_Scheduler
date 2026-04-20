---
name: Use Case
about: Adds new use case
title: ''
labels: ''
assignees: ''

---

**Use Case:** Create Account

**Description:**
Users can create an account that is either an employee or employer account.

**Actor(s):** User

**Trigger:**
User performs action to create account.

**Conditions:**
- **Pre-condition:** Email associated with account is not within the database yet.
- **Post-condition:** Account must be stored in the database.

**Acceptance Criteria:**
- User can select type: employee or employer during account creation.
- System prevents registration if email is already used.
- Successfully created accounts are stored in the database.

# LibraryManagement — Kotlin + Jetpack Compose

Ứng dụng bài tập quản lý thư viện với 3 màn hình:
- Quản lý: chọn sinh viên, chọn sách và mượn sách, thêm sách mới.
- DS Sách: xem trạng thái sách và trả sách.
- Sinh viên: xem danh sách và chọn sinh viên hiện tại.

## Cách mở
1. Giải nén file ZIP.
2. Android Studio → Open → chọn thư mục `LibraryManagement`.
3. Chờ Gradle Sync hoàn tất.
4. Chạy trên emulator hoặc thiết bị Android.

## OOP được áp dụng
- `Book`, `Student`, `BorrowRecord`: mô hình đối tượng.
- `LibraryRepository`: đóng gói dữ liệu và nghiệp vụ.
- `LibraryViewModel`: điều phối nghiệp vụ và trạng thái UI.
- Các màn hình Compose tách biệt; Navigation Compose điều hướng giữa màn hình.

## Ghi chú
Dữ liệu hiện lưu trong bộ nhớ (in-memory), chưa lưu sau khi tắt ứng dụng. Có thể mở rộng với Room Database nếu yêu cầu môn học cần lưu dữ liệu lâu dài.

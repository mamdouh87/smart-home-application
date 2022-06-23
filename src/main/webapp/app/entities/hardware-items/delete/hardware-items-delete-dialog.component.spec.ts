jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { HardwareItemsService } from '../service/hardware-items.service';

import { HardwareItemsDeleteDialogComponent } from './hardware-items-delete-dialog.component';

describe('HardwareItems Management Delete Component', () => {
  let comp: HardwareItemsDeleteDialogComponent;
  let fixture: ComponentFixture<HardwareItemsDeleteDialogComponent>;
  let service: HardwareItemsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [HardwareItemsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(HardwareItemsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HardwareItemsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(HardwareItemsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
